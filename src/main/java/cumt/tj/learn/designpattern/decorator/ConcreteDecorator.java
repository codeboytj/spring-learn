package cumt.tj.learn.designpattern.decorator;

/**
 * Created by sky on 17-11-9.
 * 实际装饰者，调用装饰者模型的方法实现对构件方法的调用，并加入新的行为
 *
 */
public class ConcreteDecorator extends Decorator{

    public ConcreteDecorator(Component component) {
        super(component);
    }

    @Override
    public void doSomething(){
        super.doSomething();
        System.out.println("加上一层华丽丽的功能");
    }

    public static void main(String[] args) {

        //需要被装饰的构件
        Component component=new ConcreteComponent();

        //使用一个装饰器
        Component componentWithD1=new ConcreteDecorator(component);

        componentWithD1.doSomething();

        //使用另外一个装饰器
        Component componentWithD2=new ConcreteDecorator2(component);

        componentWithD2.doSomething();

        //使用2层装饰器
        Component componentWithD12=new ConcreteDecorator2(componentWithD1);

        componentWithD12.doSomething();
    }

}
