# 事务管理

## 简介

对事务的支持是spring最引人注目的地方。spring提供的事务管理的抽象有如下优点：

- 为不同的事务接口提供一致的编程模型，一般用到的事务接口有：Java Transaction API (JTA), JDBC, Hibernate, Java Persistence API (JPA), and Java Data Objects (JDO).
- 支持声明式事务。
- 为复杂的事务api(如JTA)提供简单的编程接口。
- 与spring数据访问集成良好。

## spring事务支持模型的优点

### 全局事务(Global transactions)

全局事务使你能处理传统关系型数据库、消息队列等多种事务资源。服务器通过JTA管理全局事务，JTA是一套笨重的api，这与它的异常模型
有关。此外，使用JTA还必须使用JNDI。显然，全局事务的使用会限制代码重用，因为JTA通常只在一个特定的应用服务器环境中可用。

从前，使用全局事务的较好方法是利用EJB CMT (Container Managed Transaction): CMT是一种声明式事务。使用EJB CMT不需要事务相关的
JNDI查找，尽管使用EJB本身需要使用JNDI。它消除了大部分控制事务的Java code。主要的缺点是CMT与JTA和应用程序服务器环境相关。
同时，只有当选择在EJB中实现业务逻辑，或者至少在一个事务EJB的装饰之下的时候才可用。

### 局部事务(Local transactions)

局部事务是为特定的资源定制的, such as 一个JDBC的事务，不能跨多种事务资源使用。另一个缺点是侵入程序代码。

### Spring的一致性编程模型

Spring解决了global and local transactions的缺点，运行应用开发者在不同环境中使用一致的编程模型，只用写一次代码。spring提供
了编程式和声明式事务。许多人使用声明式事务，这也是推荐使用的。
    
通过编程式事务，开发者与spring事务抽象一起工作，可以在任何事务基础架构上运行。通过声明式事务，开发者需要写更少的或者甚至
不写事务管理的代码，而且不依赖spring以及其它的事务api。

## spring的事务抽象

spring事务抽象的关键是一个叫事务策略(transaction strategy)的概念。事务策略通过org.springframework.transaction.PlatformTransactionManager
接口定义：

```
public interface PlatformTransactionManager {

    TransactionStatus getTransaction(
            TransactionDefinition definition) throws TransactionException;

    void commit(TransactionStatus status) throws TransactionException;

    void rollback(TransactionStatus status) throws TransactionException;
}
```

由于PlatformTransactionManager是接口, 可以在需要的时候轻易地 mocked or stubbed。它与JNDI那样的查找策略无关。 
PlatformTransactionManager的实现类在spring IoC容器中的其它bean一样被定义。这让spring的事务成为一个
有价值的抽象，即使是使用JTA的时候也是如此。事务的代码比直接使用JTA的时候更好测试。

PlatformTransactionManager方法抛出的任何TransactionException都是RuntimeException的子类，不需要检查。
Transaction的失败都是致命的。应用代码通常不能从事务失败中恢复，开发者仍然可以选择捕捉处理TransactionException，但不被强迫。

getTransaction(..)根据TransactionDefinition参数返回一个TransactionStatus对象。这个返回的TransactionStatus也许代表一个新
事务, 或者一个已经存在在当前调用栈的事务。后一种情况的意义在于，在JavaEE的事务环境中, a TransactionStatus与一个运行的线程
相关联。

TransactionDefinition接口指定了：

1. Isolation: 隔离级别。
2. Propagation: 传播性。
3. Timeout: 超时自动回滚。
4. only status: 当代码读取而不更改数据的时候，可以使用只读事务。有时Read-only transactions非常有用, such as when you are using Hibernate. 

TransactionStatus接口提供了一个简单的方法让事务代码控制事务运行、查询事务状态。一些常用的APIs:

```
public interface TransactionStatus extends SavepointManager {

    boolean isNewTransaction();

    boolean hasSavepoint();

    void setRollbackOnly();

    boolean isRollbackOnly();

    void flush();

    boolean isCompleted();

}
```

不管是使用declarative or programmatic transaction management in Spring, 定义正确的PlatformTransactionManager实现是至关重要的。
通常通过依赖注入定义实现。

PlatformTransactionManager的实现通常需要当前数据操作环境的知识，比如 JDBC, JTA, Hibernate, and so on. 一个定义在JDBC工作
环境中定义local PlatformTransactionManager实现的例子：

首先配置JDBC的DataSource:

```
<bean id="dataSource" class="org.apache.commons.dbcp.BasicDataSource" destroy-method="close">
    <property name="driverClassName" value="${jdbc.driverClassName}" />
    <property name="url" value="${jdbc.url}" />
    <property name="username" value="${jdbc.username}" />
    <property name="password" value="${jdbc.password}" />
</bean>
```

相关的PlatformTransactionManager bean定义需要一个DataSource的引用。 通常像这样:

```
<bean id="txManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
    <property name="dataSource" ref="dataSource"/>
</bean>
```

如果在JavaEE容器中使用JTA，那么可以通过JNDI查找容器DataSource,与Spring’s JtaTransactionManager结合使用：

```
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xmlns:jee="http://www.springframework.org/schema/jee"
    xsi:schemaLocation="
        http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/jee
        http://www.springframework.org/schema/jee/spring-jee.xsd">

    <jee:jndi-lookup id="dataSource" jndi-name="jdbc/jpetstore"/>

    <bean id="txManager" class="org.springframework.transaction.jta.JtaTransactionManager" />

    <!-- other <bean/> definitions here -->

</beans>
```

JtaTransactionManager不需要了解DataSource, 或者其他指定的资源, 因为它使用容器的global transaction management.

使用Hibernate local transactions的时候，需要定义一个Hibernate LocalSessionFactoryBean，供程序代码获取Hibernate Session实例。
以下是一个简单的例子，DataSource bean 的定义JDBC例子中的差不多，就不重复写了：

```
<bean id="sessionFactory" class="org.springframework.orm.hibernate5.LocalSessionFactoryBean">
    <property name="dataSource" ref="dataSource"/>
    <property name="mappingResources">
        <list>
            <value>org/springframework/samples/petclinic/hibernate/petclinic.hbm.xml</value>
        </list>
    </property>
    <property name="hibernateProperties">
        <value>
            hibernate.dialect=${hibernate.dialect}
        </value>
    </property>
</bean>

<bean id="txManager" class="org.springframework.orm.hibernate5.HibernateTransactionManager">
    <property name="sessionFactory" ref="sessionFactory"/>
</bean>
```

这个例子中txManager是HibernateTransactionManager。与the DataSourceTransactionManager需要DataSource的引用一样,
the HibernateTransactionManager需要SessionFactory的引用。

如果使用Hibernate and Java EE 容器管理的JTA transactions,那么应该像之前JDBC的例子中的那样使用JtaTransactionManager。
```
<bean id="txManager" class="org.springframework.transaction.jta.JtaTransactionManager"/>
```

在这些例子中，应用程序的代码都不需要更改，仅仅通过更改配置来更改事务管理的方式。
