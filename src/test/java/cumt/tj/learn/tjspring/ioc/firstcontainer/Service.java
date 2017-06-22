package cumt.tj.learn.tjspring.ioc.firstcontainer;

/**
 * Created by sky on 17-6-17.
 */
public class Service {
}

interface MyService{
    int getCounter();
}

class MyServiceImpl implements MyService {
    int counter;

    public MyServiceImpl() {
        counter++;
    }

    public int getCounter() {
        return counter;
    }
}
