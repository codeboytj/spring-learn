package cumt.tj.learn.spring.ioc.firstcontainer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by sky on 17-6-17.
 */
@Configuration
public class AppConfig {
    @Bean
    //myService依赖于myDao，通过方法参数传入
    public MyService myService(MyDao myDao) {
        return new MyServiceImpl(myDao);
    }

    @Bean
    //事先需要通过@Bean方法创造一个名为myDao的Bean
    public MyDao myDao(){return new MyDaoImpl();}

}
