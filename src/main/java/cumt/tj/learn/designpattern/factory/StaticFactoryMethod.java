package cumt.tj.learn.designpattern.factory;

/**
 * Created by sky on 17-6-21.
 * 静态工厂方法，它其实并不是所谓的“工厂模式”
 * 参考 Effective Java
 * 相比与构造器，优点:
 * 1. 静态工厂方法有名称，可以用不同名称的静态工厂方法，体现出所创建对象的区别。
 * 2. 不必每次调用的时候都创建一个新对象。可以将构建好的实例缓存起来，进行重复利用，从而避免创建不必要的重复对象。
 * 3. 可以返回原返回类型的任何子类型的对象。
 * 4. 在创建参数化实例的时候，它们使代码变得更加简洁。（当然，由于JDK1.7的类型推导，这条优点就没有了）
 */
public class StaticFactoryMethod {

    private static StaticFactoryMethod staticFactoryMethod=new StaticFactoryMethod();
    private int count;

    private StaticFactoryMethod(){}

    public static StaticFactoryMethod getInstance(){

        //可以将一些繁琐的初始化逻辑放到静态工厂里面去
        staticFactoryMethod.count=4;
        return staticFactoryMethod;
    }

    public static void main(String[] args) {
        StaticFactoryMethod staticFactoryMethod=StaticFactoryMethod.getInstance();
        System.out.print(staticFactoryMethod.count);
    }
}
