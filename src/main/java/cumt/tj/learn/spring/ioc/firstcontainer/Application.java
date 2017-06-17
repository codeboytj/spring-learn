package cumt.tj.learn.spring.ioc.firstcontainer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Created by sky on 17-6-17.
 */
public class Application {
    @Autowired
    MyService myService1;

    public static void main(String[] args) {
        //AnnotationConfigApplicationContext这是类是Spring3.0新出来的
        //它可以接受@Configuration注解的类作为输入, 也可以接受@Component注解的类以及JSR-330元数据注解的类作为输入
        ApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);

        //当@Configuration作为输入的时候，@Configuration注解的类以及里面用@Bean注解的方法都会被定义成bean
        AppConfig myConfig = ctx.getBean(AppConfig.class);
        System.out.println(myConfig.toString());

        MyService myService = ctx.getBean(MyService.class);
        myService.showCounter();

    }
}
