package cumt.tj.learn.spring.ioc.firstcontainer;

/**
 * Created by sky on 17-6-17.
 */
public class Service {
}

interface MyService{
    void showCounter();
}

class MyServiceImpl implements MyService{
    int counter;

    public MyServiceImpl() {
        counter++;
    }

    public void showCounter() {
        System.out.println(counter);
    }
}
