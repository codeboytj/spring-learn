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
