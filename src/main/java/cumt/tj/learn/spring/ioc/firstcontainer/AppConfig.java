package cumt.tj.learn.spring.ioc.firstcontainer;

import org.springframework.beans.factory.annotation.Autowire;
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

    @Bean(autowire = Autowire.BY_NAME)
    public AutoWireServiceImpl autoWireService(){
        //使用自动装配的方式装配AutoWireServiceImpl中的MyDao，这样，就不用单独声明一个带有参数的构造函数了
        return new AutoWireServiceImpl();
    }

    @Bean
    public MyDao autoWireDao(){
        //当然，即使是自动装配，定义bean还是需要的
        return new AutoWireDaoImpl();
    }

}
