package cumt.tj.learn.designpattern.decorator;

/**
 * Created by sky on 17-11-9.
 * 装饰者模型，包含一个构件的引用，暴露一个接口供实际装饰者使用，不让实际装饰者直接调用构件
 */
public class Decorator implements Component{

    private Component component;

    public Decorator(Component component) {
        this.component = component;
    }

    public void doSomething() {

        component.doSomething();

    }
}
