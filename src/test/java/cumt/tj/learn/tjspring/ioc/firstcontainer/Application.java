package cumt.tj.learn.tjspring.ioc.firstcontainer;

import cumt.tj.learn.tjspring.context.TAnnotationConfigApplicationContext;
import cumt.tj.learn.tjspring.context.TApplicationContext;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.lang.reflect.InvocationTargetException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by sky on 17-6-17.
 */
public class Application {

    @Test
    public void ioc()throws IllegalAccessException, InvocationTargetException, InstantiationException {

        TApplicationContext ctx = TAnnotationConfigApplicationContext.createByConfiguration(AppConfig.class);

        AppConfig myConfig = ctx.getBean(AppConfig.class);

        MyService myService = ctx.getBean(MyService.class);
        assertEquals(1,myService.getCounter());
        assertNotNull(myConfig);
        assertNotNull(myService);

    }
}
