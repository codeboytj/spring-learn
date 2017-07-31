package cumt.tj.learn.spring.aop;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * Created by sky on 17-7-31.
 */
@Configuration
@EnableAspectJAutoProxy
public class AppConfig {

    @Bean
    public NotVeryUsefulAspect myAspect(){
        return new NotVeryUsefulAspect();
    }

}
