package cumt.tj.learn.tjspring.annotation;

import java.lang.annotation.*;

/**
 * Created by sky on 17-6-17.
 */
//Target元注解用来定义你的注解用于什么地方，一个方法或者一个域以及等等
@Target(ElementType.METHOD)
//Retention用来表示注解将被保留到哪个级别，源代码、class文件级别以及运行时级别
@Retention(RetentionPolicy.RUNTIME)
//Documented表示注解会被包含在JavaDoc中
@Documented
public @interface TBean {

}
