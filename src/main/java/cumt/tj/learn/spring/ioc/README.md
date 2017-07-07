#  控制反转(IoC)

IoC也被成为依赖注入(DI)。它是个什么意思呢？

IoC的几个相关概念：

- beans：在Spring中，被SpringIoC管理的那些组成应用骨架的对象。
- bean：被SpringIoC实例化、组合以及管理的对象。

beans和它们之间的依赖关系，被写在容器使用的配置文件之中。

## 1. IoC容器

容器通过读取配置元数据，实例化、配置以及组装beans，最后形成一个配置完全、可运行的系统。

### 1.1. 配置元数据

配置元数据可以有三种形式：

1. xml文件，这是最初的配置方式
2. 基于注解的配置，从Spring2.5开始支持
3. 使用Java代码进行配置，从Spring3.0开始支持

我决定只学最后一种配置方式。通常我们不对细粒度的domain对象进行配置，因为它是属于DAO层和业务层逻辑的任务。但是可以
通过AspectJ(aop的一个东东吧)进行配置，在IoC容器外部进行创建。

#### 1.1.1. Java代码配置

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

### 1.2. 实例化容器

org.springframework.context.ApplicationContext接口代表的就是SpringIoc容器。实例化容器，就是实例化ApplicationContext接口的一个实现类。
对于使用Java代码进行的配置，可以想[Application](./firstcontainer/Application.java)中的那样使用AnnotationConfigApplicationContext带
Java配置类的构造函数启动容器。

### 1.3. 使用容器

ApplicationContext是一个注册了不同beans和他们之间关系的工厂，它提供了一个T getBean()方法，可以从中取回bean，然后就可以使
用bean的一些方法。除了getBean方法之外，ApplicationContext还提供了其他取回bean的方法，但是这些接口（包括getBean()）都不建
议使用，这样会造成程序对Spring APIs的依赖。
Spring与其他如web框架进行整合的时候，为各种框架的组件（如controllers和JSF-managed beans）提供了依赖注入，允许通过元数据
对特定的bean声明一个依赖（比如，使用autowiring注解）

### 1.4. 依赖注入(DI)

在Java代码配置的IoC元配置中，要实现依赖注入，需要往[@Bean注解方法中传入参数](./firstcontainer/AppConfig.java)，需要在使用之前
通过另一个@Bean方法定义注入的Bean，类似与xml的构造器方式的依赖注入。真正的依赖注入逻辑是在方法里面，是自己写的，可以用
构造器、静态工厂方法以及setter等其他方式。
```

    @Bean
    //myService依赖于myDao，通过方法参数传入
    public MyService myService(MyDao myDao) {
        return new MyServiceImpl(myDao);
    }

    @Bean
    //事先需要通过@Bean方法创造一个名为myDao的Bean
    public MyDao myDao(){return new MyDaoImpl();}
    
```
#### 1.4.1. 依赖注入过程

1. spring创建的时候，会检查每个bean的配置。
    1. 环形依赖(circular dependencies)，一个bean A创建的时候依赖于bean B，而bean B创建的时候依赖于bean A，这时候spring
    IoC容器就会报BeanCurrentlyInCreationException错误。
    2. 不存在的bean依赖，关于这个错误，如果使用的是Java代码的配置方法，在使用idea等IDE进行编译的时候就可以避免。
2. 在创建Bean的时候，Spring会尽可能晚地解析依赖以及设置属性。这就意味着，容器会正常创建，但是当使用某个对象并出错的时候，会
产生一个迟到的异常。
3. 然而，单例scope的bean和被设置为"pre-instantiated"的bean会在容器创建的时候就创建。在创建容器的时候，这会花费一些时间和
内存占用，但是让我们能够在容器启动的时候，发现配置错误，避免一些迟到的异常。其他的bean要在需要的时候才被创建。另外，bean的
scope可以更改。
4. 如果没有环形依赖，当多个bean合作创建一个dependent bean的时候，每个bean都会先于dependent bean创建。

#### 1.4.2. @DependsOn

通常，如果bean A依赖bean B，那么B很可能是A的一个field。这时候我们通过Java代码风格的元配置，使用
构造器或setter方法设置依赖。但是，有些情况下，依赖并不是通过field来表达的，而是通过其它bean的初始化
，例如数据库驱动的注册。这时候就要使用到@DependsOn注解了。

@DependsOn注解可以用在使用@Component注解的类上面，也可以使用在@Bean注解的方法上面。当使用在类上面的时候，
需要进行注解扫描，否则不会起作用，并且，如果类是通过xml的方式声明的，@DependsOn会被忽略，取而代之的是
<bean>中的depends-on属性。

#### 1.4.3. 懒惰初始化

正如前面提到的那样，作为容器初始化的过程的一部分，容器会急切地创建和配置所有单例bean。当这种行为不需要的时候，
可以通过使用@Lazy注解的方式，让相应bean的初始化过程延迟。@Lazy注解会告诉容器，当第一次使用该bean的时候再进行
初始化，而不是在启动的时候。

@Lazy注解表示bean需要被延迟初始化，可以用在使用@Component注解的类上面，也可以使用在@Bean注解的方法上面。如果使用了
@Lazy并设为true，直到被另一个bean用到的时候或者被BeanFactory显式地检索的时候才被初始化。如果设为false，bean还是会在
容器启动的时候初始化。

如果@Lazy使用在@Configuration类上面，表示该类的所有@Bean方法都需要延迟初始化，除非方法上使用了@Lazy并设置为false。

#### 1.4.4. 自动装配

自动装配有如下的优势：

1. 显著地减少指定属性或构造器参数的需要。
2. 当对象改变的时候不用更新配置(大概就是这个意思)。这在开发的过程中非常有用。

在基于Java代码的配置中，如果要使用自动装配，需要指定@Bean注解的autowire为Autowire.BY_NAME或Autowire.BY_TYPE：

```
@Bean(autowire = Autowire.BY_NAME)
public AutoWireServiceImpl autoWireService(){
    //使用自动装配的方式装配AutoWireServiceImpl中的MyDao，这样，就不用单独声明一个带有参数的构造函数了
    return new AutoWireServiceImpl();
}

@Bean
public MyDao autoWireDao(){
    //当然，即使是自动装配，定义bean还是需要的
    return new AutoWireDaoImpl();
}
```


需要注意的是，自动装配是利用setter方法弄的，如果需要自动装配的那个属性没有setter方法，是不会装配成功的：

```
class AutoWireServiceImpl implements MyService{
    int counter;
    MyDao autoWireDao;

    public void showCounter() {
        System.out.println(counter);
    }

    //查看自动装配是否成功
    public boolean ifDaoExist() {
        return autoWireDao!=null;
    }

    //当使用自动装配的时候，需要声明一个setter方法，否则自动装配不会成功
    public void setAutoWireDao(MyDao autoWireDao) {
        this.autoWireDao = autoWireDao;
    }
}
```

自动装配的一些限制：

1. 自动装配不能用于基本类型（及其数组）、String与Class。
2. 不如通过代码显式装配那么精确。
3. 装配信息可能不会被从Spring容器中生成文档的工具使用到。
4. 容器中多个bean的定义可能会匹配到setter方法的参数type。这可能会造成一些困惑。

#### 1.4.5. 方法注入

在大多数情况下，容器中的大多数beans都是单例的，它们之间的依赖注入是很容易通过设置field实现的。当互相依赖的bean的scope不一致的时候，
就会出现问题。例如，一个单例(single)bean A需要使用一个原型(prototype)bean B，容器只会创建A一次，会在这个时候创建B并设置
到相应的属性，容器并不会在没有A需要使用B的时候都创建一次。这时，就要通过方法来获取依赖，而不是通过属性。

##### 1.4.5.1. 一种解决方案

让A实现ApplicationContextAware接口，这样A中就有了其所在容器的引用。当A需要使用B的时候，通过调用容器的getBean()方法获得B：

```
public class CommandManager implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    public Object process(Map commandState) {
        // grab a new instance of the appropriate Command
        Command command = createCommand();
        // set the state on the (hopefully brand new) Command instance
        command.setState(commandState);
        return command.execute();
    }

    protected Command createCommand() {
        // notice the Spring API dependency!
        return this.applicationContext.getBean("command", Command.class);
    }

    public void setApplicationContext(
            ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
```

这并不是一种理想的方式，因为业务代码与SpringIoC容器产生了耦合。

##### 1.4.5.2. Lookup method inject

通过Java风格的元配置，可以将A获取B的方法声明成抽象方法：

```
public abstract class CommandManager {
   public Object process(Object commandState) {
       // grab a new instance of the appropriate Command interface
       Command command = createCommand();
       // set the state on the (hopefully brand new) Command instance
       command.setState(commandState);
       return command.execute();
   }

   // okay... but where is the implementation of this method?
   //获取B的方法
   protected abstract Command createCommand();
}
   
```

然后，在配置B的时候，声明成原型：

```
@Bean
@Scope("prototype")
public AsyncCommand asyncCommand() {
   AsyncCommand command = new AsyncCommand();
   // inject dependencies here as required
   return command;
}
```

最后，在配置A的时候，实现抽象方法：

```
@Bean
public CommandManager commandManager() {
    // return new anonymous implementation of CommandManager with command() overridden
    // to return a new prototype Command object
    return new CommandManager() {
        //实现抽象方法，每次调用方法都返回一个原型的bean
        protected Command createCommand() {
            return asyncCommand();
        }
    }
}
```

## 2. [Bean scopes](./scpoe)
