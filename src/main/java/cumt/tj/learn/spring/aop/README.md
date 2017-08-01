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

#### 1.1.4. 声明通知(Advice)

通知是与切入点表达式联合使用的，在与切入点匹配的方法执行之前或之后执行。
切入点表达式可以是之前通过@PointCut声明的切入点的名称：

```
@Aspect
public class BeforeExample {

    @Before("com.xyz.myapp.SystemArchitecture.dataAccessOperation()")
    public void doAccessCheck() {
        // ...
    }

}
```

这样的通知利用之前在SystemArchitecture中定义的名为dataAccessOperation的切入点的引用
作为表达式，表示此声明在dataAccessOperation匹配的方法执行之前执行。

除此之外，也可以用一个单独切入点表达式声明代替：

```
@Aspect
public class BeforeExample {

    @Before("execution(* com.xyz.myapp.dao.*.*(..))")
    public void doAccessCheck() {
        // ...
    }

}
```

效果一样。通知有多种，具体见附录。

##### 1.1.4.1. 为通知传递参数

可以为声明的通知方法传递参数，这样可以到达与连接点联系起来的效果，如：

```aidl
@Aspect
public class AroundExample {

    @Around("com.xyz.myapp.SystemArchitecture.businessService()")
    public Object doBasicProfiling(ProceedingJoinPoint pjp) throws Throwable {
        // start stopwatch
        Object retVal = pjp.proceed();
        // stop stopwatch
        return retVal;
    }

}
```

ProceedingJoinPoint类继承自org.aspectj.lang.JoinPoint接口，该接口暴露了许多很有用的方法：

- getArgs()，返回连接点方法的参数
- getThis()，返回代理对象
- getTarget()，返回目标对象
- getSignature()，返回被通知方法的描述

每个通知方法可以把JoinPoint作为第一个参数，对于around advice，需要将ProceedingJoinPoint作为第一个参数。
类似的，可以通过after advice方法的参数获得被通知方法执行的返回值、抛出的异常等。另外，还有在advice声明表达式
中使用args()来传递参数的方法。

##### 1.1.4.2. 通知的执行顺序

如果对于一个连接点，定义了多个通知，SpringAop会使用AspectJ一样的优先规则执行通知：

- 在"On The Way In"（进入）连接点的时候，如两个@Before通知，优先级高的advice会先执行。
- 在"On The Way Out"（走出）连接点的时候，如两个@After通知，优先级高的advice会后执行。

对于不同切面中，对同一连接点的多个advice，可以通过@Order注解给切面类设置优先级，如@Order(5)，
越小的值拥有越高的优先级。或者可用通过在aspect类中实现org.springframework.core.Ordered接口设置优先级。

对于同一切面中，对同一连接点的多个相同的advice，执行顺序是无法确定的。这种情况下，可以考虑将这样的多个相同的advice
合成一个，或者分离到多个aspect类中去。

## 2. 附录

### 2.1. AOP的重要概念与术语

- Aspect，切面，将横切关注点设计为独立可重用的对象，这些对象称为切面。

### 2.2. Spring中支持的PCD

- execution，用来匹配方法执行连接点，这是最常用的。
- within，用来匹配指定类型中定义的那些方法的执行。

### 2.3. 通知（Advice）种类

- Before advice: Advice that executes before a join point, but which does not have the ability to prevent execution flow proceeding to the join point (unless it throws an exception).
- After returning advice: Advice to be executed after a join point completes normally: for example, if a method returns without throwing an exception.
- After throwing advice: Advice to be executed if a method exits by throwing an exception.
- After (finally) advice: Advice to be executed regardless of the means by which a join point exits (normal or exceptional return).
- Around advice: Advice that surrounds a join point such as a method invocation. This is the most powerful kind of advice. Around advice can perform custom behavior before and after the method invocation. It is also responsible for choosing whether to proceed to the join point or to shortcut the advised method execution by returning its own return value or throwing an exception. 