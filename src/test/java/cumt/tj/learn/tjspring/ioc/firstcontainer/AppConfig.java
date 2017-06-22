package cumt.tj.learn.tjspring.ioc.firstcontainer;

import cumt.tj.learn.tjspring.annotation.TBean;
import cumt.tj.learn.tjspring.annotation.TConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by sky on 17-6-17.
 */
@TConfiguration
public class AppConfig {
    @TBean
    public MyService myService() {
        return new MyServiceImpl();
    }

}
