package cumt.tj.learn.spring.tx;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by sky on 17-9-11.
 */
public final class Application {

    public static void main(final String[] args) throws Exception {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("/tx/context.xml", Application.class);
       FooService fooService = (FooService) ctx.getBean("fooService");
        fooService.insertFoo (new Foo());
    }

}
