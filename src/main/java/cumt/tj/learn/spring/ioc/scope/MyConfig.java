package cumt.tj.learn.spring.ioc.scope;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * Created by sky on 17-7-4.
 */
@Configuration
public class MyConfig {

    @Bean
    @Scope("singleton")
    public MyService myService(){
        return new MyServiceImpl();
    }

}
