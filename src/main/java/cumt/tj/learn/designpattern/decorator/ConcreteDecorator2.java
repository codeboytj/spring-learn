package cumt.tj.learn.designpattern.decorator;


/**
 * Created by sky on 17-11-9.
 * 实际装饰者，调用装饰者模型的方法实现对构件方法的调用，并加入新的行为
 */
public class ConcreteDecorator2 extends Decorator{

    public ConcreteDecorator2(Component component) {
        super(component);
    }

    @Override
    public void doSomething(){
        super.doSomething();
        System.out.println("加上一层美丽的装饰");
    }
}
