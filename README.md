#  Spring设计思想

## 1. 设计模式

1. [代理模式](./src/main/java/cumt/tj/learn/designpattern/proxy)
    1. [静态代理](./src/main/java/cumt/tj/learn/designpattern/proxy/StaticProxy.java)
    2. [动态代理](./src/main/java/cumt/tj/learn/designpattern/proxy/DynamicProxy.java)
    2. [Cglib代理](./src/main/java/cumt/tj/learn/designpattern/proxy/CglibProxy.java)
2. [单例模式](./src/main/java/cumt/tj/learn/designpattern/singleton)
    1. [线程不安全的单例](./src/main/java/cumt/tj/learn/designpattern/singleton/SimpleSingleton.java)
    2. [急切、简单却安全的单例](./src/main/java/cumt/tj/learn/designpattern/singleton/EagerlySingleton.java)
    3. [同步方法实现的线程安全的单例](./src/main/java/cumt/tj/learn/designpattern/singleton/SynchronizedSingleton.java)
    4. [双重检查加锁的减少同步的线程安全单例](./src/main/java/cumt/tj/learn/designpattern/singleton/BetterSynchronizedSingleton.java)
3. [工厂模式](./src/main/java/cumt/tj/learn/designpattern/factory)
    1. [静态工厂方法](./src/main/java/cumt/tj/learn/designpattern/factory/StaticFactoryMethod.java)
    2. [简单工厂方法](./src/main/java/cumt/tj/learn/designpattern/factory/SimpleFactory.java)
    3. [工厂模式](./src/main/java/cumt/tj/learn/designpattern/factory/Factory.java)
    4. [抽象工厂模式](./src/main/java/cumt/tj/learn/designpattern/factory/AbstractFactory.java)
4. [装饰者模式](./src/main/java/cumt/tj/learn/designpattern/decorator)
    
## 2. 核心技术

1. [控制反转(IoC)](./src/main/java/cumt/tj/learn/spring/ioc)
2. [面向切面编程(AOP)](./src/main/java/cumt/tj/learn/spring/aop)

## 3. [动手写spring](./src/main/java/cumt/tj/learn/tjspring)

1. [注解](./src/main/java/cumt/tj/learn/tjspring/annotation)
2. [ioc容器](./src/main/java/cumt/tj/learn/tjspring/context)
