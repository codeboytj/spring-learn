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

#### 1.1.3. 声明切入点(Pointcut)

声明一个切入点包含2个部分：

1. 定义切入点签名，包含名称和参数
2. 切入点表达式，定义具体匹配的方法执行

```
@Pointcut("execution(* transfer(..))")// the pointcut expression
private void anyOldTransfer() {}// the pointcut signature
```

这样便定义了一个名为anyOldTransfer切入点，匹配任何名为transfer方法的切入点。

##### 1.1.3.1. 切入点指示符（Pointcut Designators、PCD）

之前定义切入点表达式时，用到的execution就是一个切入点指示符，用来匹配执行方法的连接点。
Spring中除了支持AspectJ中的部分PCD外，还支持一个自己的名为bean的PCD。Spring支持的PCD见附录。

[SystemArchitecture](./SystemArchitecture.java)展示了一个经常运用在企业应用中的切入点定义以供参考。

## 2. 附录

### 2.1. AOP的重要概念与术语

- Aspect，切面，将横切关注点设计为独立可重用的对象，这些对象称为切面。

### 2.2. Spring中支持的PCD

- execution，用来匹配方法执行连接点，这是最常用的。
- within，用来匹配指定类型中定义的那些方法的执行。
