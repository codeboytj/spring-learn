package cumt.tj.learn.designpattern.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by sky on 17-6-14.
 * 动态代理
 * 动态代理是Java反射机制类库中的Proxy类实现的，解决了静态代理繁琐实现接口的缺口
 */
public class DynamicProxy {

    public static void main(String[] args) {
        //目标对象
        final RealObject ro=new RealObject();

        //利用java反射机制提供的类库实现动态代理
        Interface proxy= (Interface) Proxy.newProxyInstance(
                Interface.class.getClassLoader(),
                //代理类需要实现的接口类
                new Class[]{Interface.class},
                //
                new InvocationHandler() {
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        //代理类的方法执行逻辑，额外做的事写在这里面
                        //如果对不同方法做不同的事情，需要使用If判断method.getName()以及method.getDescriptor()
                        System.out.println(method.getName()+"方法开始执行");
                        //调用目标对象的方法，args为传入方法的参数
                        Object o=method.invoke(ro,args);
                        System.out.println(method.getName()+"方法执行结束");
                        return o;
                    }
                });

        proxy.doSomething();
        proxy.doSomethingElse();
    }

}
