#  Bean scopes

Spring为bean提供了多种scope，对于Java风格的配置，可以通过在@Bean方法上设置@Scope
注解来实现：

```
@Configuration
public class MyConfig {

    @Bean
    @Scope("singleton")
    public MyService myService(){
        return new MyServiceImpl();
    }

}  
```

## 1. 单例(The singleton scope)

单例是SpringIoC容器默认使用的scope。当一个bean被指定成单例bean，容器会为它创建一个
共享的对象。对于所有利用单例bean的id访问该bean的请求，Spring都会返回这一个共享对象。

与设计模式中的单例模式不同的是，单例模式是一个类加载器创建一个单例，而Spring的单例
是“一个容器，一个单例”。

## 2. 原型(The prototype scope)

与单例不同的是，SpringIoC容器会为接收到的每一个对原型bean的请求，都创建一个新的对象。

Spring对于原型bean来说，就相当于new操作符，包括销毁之类的bean的所有其它生命周期需要
客户端自行控制。

## 3. request、session、global session、application以及WebSocket scopes

这些都是web里面的东西，使用他们需要使用web容器，如XmlWebApplicationContext，不然会报错。
另外如果使用的是Spring Web MVC，使用Spring的DispatcherServlet或者DispatcherPortlet处理请求
，不需要进行另外的配置，而如果使用的是Servlet2.5的web容器，不是使用Spring的
DispatcherServlet处理请求（JSF或Struts），就需要进行额外配置。

对于request、session、以及application，JavaWeb中有几个同名的东东Http request、Http session
以及application(ServletContext)。request scope的bean就是跟随http request创建而产生，销毁而
销毁，两个request请求产生的bean是独立的。其他类推

而对于global session，对应的是portlet的global session。

### 3.1. 使用aop代理进行依赖注入

对于这几种web scope，由于它们不应该是单例的，所以，当将它们注入到单例bean中的时候，要额外小心。
Java风格的配置：

```
// an HTTP Session-scoped bean exposed as a proxy
@Bean
@SessionScope
public UserPreferences userPreferences() {
    return new UserPreferences();
}

@Bean
public Service userService() {
    UserService service = new SimpleUserService();
    // a reference to the proxied userPreferences bean
    service.setUserPreferences(userPreferences());
    return service;
}
```

## 4. 自定义scope

Spring允许用户自定义scope，需要实现org.springframework.beans.factory.config.Scope接口，

如果要使用自定义的scope，首先需要让Spring知道你定义了一个scope，可以使用org.springframework.beans.factory.config.Scope
的`void registerScope(String scopeName, Scope scope);`方法注册scope的名字和实现类。然后就
可以通过平常的方法使用scope了，但是对于AnnotationConfigApplicationContext来说，
并没有实现这一接口。而且官网也没有介绍Java风格配置怎么用自定义scope，所以，现在还不知道
怎么用。