package cumt.tj.learn.tjspring.context;

import org.springframework.context.ApplicationContext;

/**
 * Created by sky on 17-6-21.
 * 这个类根据Java代码风格的元配置来实例化、组合与初始化
 */
public class TAnnotationConfigApplicationContext implements TApplicationContext{

    public static <E> ApplicationContext createByConfiguration(Class<E> clz){

        //根据clz里面的@TBean注解来创建容器中Beans

    }

    public <T> T getBean(Class<T> tClass) {
        return null;
    }
}
