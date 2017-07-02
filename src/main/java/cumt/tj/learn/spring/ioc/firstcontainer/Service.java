package cumt.tj.learn.spring.ioc.firstcontainer;

/**
 * Created by sky on 17-6-17.
 */
public class Service {
}

interface MyService{
    void showCounter();
    boolean ifDaoExist();
}

class MyServiceImpl implements MyService{
    int counter;
    MyDao myDao;

    public MyServiceImpl() {
        counter++;
    }

    public MyServiceImpl(MyDao myDao){this.myDao=myDao;}
    public void showCounter() {
        System.out.println(counter);
    }

    public boolean ifDaoExist() {
        return myDao!=null;
    }
}

class AutoWireServiceImpl implements MyService{
    int counter;
    MyDao autoWireDao;

    public void showCounter() {
        System.out.println(counter);
    }

    //查看自动装配是否成功
    public boolean ifDaoExist() {
        return autoWireDao!=null;
    }

    //当使用自动装配的时候，需要声明一个setter方法，否则自动装配不会成功
    public void setAutoWireDao(MyDao autoWireDao) {
        this.autoWireDao = autoWireDao;
    }
}
