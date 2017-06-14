package cumt.tj.learn.designpattern.proxy;

/**
 * Created by sky on 17-6-13.
 * 静态代理模式，代理对象，代理对象可以在调用目标对象方法的时候进行额外的操作，而不改变目标对象本身的实现，这是设计模式对修改封闭，对扩展开放的原则
 * 缺点：为了实现代理模式，要创建一个代理类，实现接口，在接口方法太多的时候，非常麻烦，于是有了动态代理。
 */

interface Interface{
    void doSomething();
    void doSomethingElse();
}

//目标对象，被代理对象，实现操作的真正对象
class RealObject implements Interface{
    public void doSomething() {
        System.out.println("doSomething");
    }

    public void doSomethingElse() {
        System.out.println("doSomethingElse");
    }
}

//代理对象，代理对象可以在调用目标对象方法的时候进行额外的操作，而不改变目标对象本身的实现，这是设计模式对修改封闭，对扩展开放的原则
public class StaticProxy implements Interface{
    private RealObject ro=new RealObject();

    public void doSomething() {

        //客户端调用我，而不是目标对象，是为了调用目标对象的方法的时候，实现一些额外的操作，比如打印到控制台，方法的执行状态
        System.out.println("doSomething方法开始执行");
        ro.doSomething();
        System.out.println("doSomething方法执行结束");
    }

    public void doSomethingElse() {
        System.out.println("doSomethingElse方法开始执行");
        ro.doSomethingElse();
        System.out.println("doSomethingElse方法执行结束");
    }

    public static void main(String[] args) {
        StaticProxy sp=new StaticProxy();
        sp.doSomething();
        sp.doSomethingElse();
    }
}
