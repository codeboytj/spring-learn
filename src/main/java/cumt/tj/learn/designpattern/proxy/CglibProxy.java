package cumt.tj.learn.designpattern.proxy;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * Created by sky on 17-6-30.
 * cglib动态代理
 * 对于JDK的动态代理模式，需要目标对象实现某一接口，
 * 但实际上有时候目标对象只是一个单独的对象,并没有实现任何的接口,
 * 这个时候就可以使用以目标对象子类的方式类实现代理,这种方法就叫做:Cglib代理
 * Cglib代理,也叫作子类代理,它是在内存中构建一个子类对象从而实现对目标对象功能的扩展.
 * Cglib包的底层是通过使用一个小而块的字节码处理框架ASM来转换字节码并生成新的类.不鼓励直接使用ASM,因为它要求你必须对JVM内部结构包括class文件的格式和指令集都很熟悉.
 */
public class CglibProxy implements MethodInterceptor{

    //目标对象
    Object target;

    public Object getInstance(Object target){
        this.target=target;

        //利用字节码创建对象的工具类
        Enhancer en=new Enhancer();
        //设置超类
        en.setSuperclass(target.getClass());
        //设置回调方法
        en.setCallback(this);
        //创建子类对象
        return en.create();

    }

    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {

        System.out.println(method.getName()+"方法开始执行");
        //调用目标对象的方法，传入方法的参数
        Object result=method.invoke(target,objects);
        System.out.println(method.getName()+"方法执行结束");

        return result;

    }

    public static void main(String[] args) {
        CglibProxy proxy=new CglibProxy();
        Do doIt=(Do)proxy.getInstance(new Do());
        doIt.doSomething();
        doIt.doSomethingElse();
    }
}

class Do {

    public void doSomething() {
        System.out.println("doSomething");
    }

    public void doSomethingElse() {
        System.out.println("doSomethingElse");

    }
}
