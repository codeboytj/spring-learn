package cumt.tj.learn.tjspring.context;

import cumt.tj.learn.spring.ioc.firstcontainer.Application;
import cumt.tj.learn.tjspring.annotation.TBean;
import cumt.tj.learn.tjspring.annotation.TConfiguration;
import org.springframework.context.ApplicationContext;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sky on 17-6-21.
 * 这个类根据Java代码风格的元配置来实例化、组合与初始化
 */
public class TAnnotationConfigApplicationContext implements TApplicationContext{

    //用来存储bean的name-object映射
    Map<String,Object> beans=new HashMap<String,Object>();
    //用来存储bean的Class-name映射
    Map<Class,String> nameMap=new HashMap<Class, String>();

    /**
     * 根据@TConfiguration注解类创建容器的静态工厂方法
     * @param clz @TConfiguration注解的类
     * @param <E>
     * @return ioc容器
     * @throws IllegalAccessException 类或方法的访问权限异常
     * @throws InstantiationException @TConfiguration注解类的实例化异常
     * @throws InvocationTargetException @TBean注解方法调用异常
     */
    public static <E> TApplicationContext createByConfiguration(Class<E> clz) throws IllegalAccessException, InstantiationException, InvocationTargetException {

        TAnnotationConfigApplicationContext tApplicationContext=new TAnnotationConfigApplicationContext();

        //根据@TConfiguration注解标注的clz里面的@TBean注解的方法来创建容器中Beans
        if(clz.getAnnotation(TConfiguration.class)==null){
            //clz并没有标注@TConfiguration注解
            throw new RuntimeException("类"+clz.getName()+"并不是@Configuration标注的类");
        }

        //把配置类的bean加入容器
        Object o=clz.newInstance();
        String classBeanName=String.valueOf(clz.getName().charAt(0)).toLowerCase()+clz.getName().substring(1);
        tApplicationContext.beans.put(classBeanName,o);
        tApplicationContext.nameMap.put(clz,classBeanName);

        //找到标注了@TBean注解的方法
//        List<Method> beanMethods=new ArrayList<Method>();
        for (Method m:clz.getDeclaredMethods()) {
            if(m.getAnnotation(TBean.class)!=null){
                //调用@TBean注解的方法获得Beans
                m.setAccessible(true);
                tApplicationContext.beans.put(m.getName(),m.invoke(o,new Object[]{}));
                tApplicationContext.nameMap.put(m.getReturnType(),m.getName());
//                beanMethods.add(m);
            }
        }

        return tApplicationContext;

    }

    public <T> T getBean(Class<T> tClass) {

        //找到bean对应的名称
        String beanName=nameMap.get(tClass);

        if(beanName!=null){
            //找到了相应名称
            return (T)beans.get(beanName);
        }

        return null;
    }
}
