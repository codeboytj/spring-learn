# 控制反转(IoC)

IoC也被成为依赖注入(DI)。它是个什么意思呢？

IoC的几个相关概念：

- beans：在Spring中，被SpringIoC管理的那些组成应用骨架的对象。
- bean：被SpringIoC实例化、组合以及管理的对象。

beans和它们之间的依赖关系，被写在容器使用的配置文件之中。

## IoC容器

容器通过读取配置元数据，实例化、配置以及组装beans，最后形成一个配置完全、可运行的系统。

### 配置元数据

配置元数据可以有三种形式：

1. xml文件，这是最初的配置方式
2. 基于注解的配置，从Spring2.5开始支持
3. 使用Java代码进行配置，从Spring3.0开始支持

我决定只学最后一种配置方式。通常我们不对细粒度的domain对象进行配置，因为它是属于DAO层和业务层逻辑的任务。但是可以
通过AspectJ(aop的一个东东吧)进行配置，在IoC容器外部进行创建。

#### Java代码配置

两个重要的注解：
- @Configuration，这个注解出现在类上，它可以表示bean的定义，同时，它允许类中的方法以@Bean注解，定义一些内部bean的依赖
- @Bean，这个注解用在一个实例化、配置以及初始化新对象到SpringIoC容器的方法的方法上面，可以用在任何以@Component注解类的方法上
但是，经常与@Configuration一起用

这两个注解被建议总是在[一起用](./firstcontainer/AppConfig.java)，这样会避免一些微妙的bug的产生

```
@Configuration
public class AppConfig {

    @Bean
    public MyService myService() {
        return new MyServiceImpl();
    }

}
```

### 实例化容器

org.springframework.context.ApplicationContext接口代表的就是SpringIoc容器。实例化容器，就是实例化ApplicationContext接口的一个实现类。
对于使用Java代码进行的配置，可以想[Application](./firstcontainer/Application.java)中的那样使用AnnotationConfigApplicationContext带
Java配置类的构造函数启动容器。

### 使用容器

ApplicationContext是一个注册了不同beans和他们之间关系的工厂，它提供了一个T getBean()方法，可以从中取回bean，然后就可以使
用bean的一些方法。除了getBean方法之外，ApplicationContext还提供了其他取回bean的方法，但是这些接口（包括getBean()）都不建
议使用，这样会造成程序对Spring APIs的依赖。
Spring与其他如web框架进行整合的时候，为各种框架的组件（如controllers和JSF-managed beans）提供了依赖注入，允许通过元数据
对特定的bean声明一个依赖（比如，使用autowiring注解）
