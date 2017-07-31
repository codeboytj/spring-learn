#  面向切面编程(AOP)

关于AOP的一些概念和术语，见最后的部分

## 1. 使用方法

### 1.1. @AspectJ风格的注解

AspectJ与Spring AOP是AOP的两种实现，SpringAop支持AspectJ风格的注解。
当然，先要有AspectJ的包aspectjweaver.jar，1.6.8版本或以上的。

```
    //@AspectJ风格注解的支持
    compile group: 'org.aspectj', name: 'aspectjweaver', version: '1.8.10'
```

#### 1.1.1. 打开@AspectJ风格注解支持

如果使用Java风格的配置，可以在@Configuration注解的配置类上
使用@EnableAspectJAutoProxy注解打开@AspectJ风格注解支持：

```
   @Configuration
   @EnableAspectJAutoProxy
   public class AppConfig {
   
   }
```

#### 1.1.2. 声明切面(aspect)

可以在类上使用@Aspect注解声明一个Aspect：

```
@Aspect
public class NotVeryUsefulAspect {

}
```

然后，把切面放到IoC容器之中：

```
    @Bean
    public NotVeryUsefulAspect myAspect(){
        return new NotVeryUsefulAspect();
    }
```

这样，名为"myAspect"的切面就在IoC容器中了。

## 2. AOP的重要概念与术语

- Aspect，切面，将横切关注点设计为独立可重用的对象，这些对象称为切面。
