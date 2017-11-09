package cumt.tj.learn.designpattern.decorator;

/**
 * Created by sky on 17-11-9.
 * 实际构件，需要被装饰的东东
 */
public class ConcreteComponent implements Component{

    public void doSomething() {
        System.out.println("原始功能");
    }
}
