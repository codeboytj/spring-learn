package cumt.tj.learn.designpattern.factory;

/**
 * Created by sky on 17-6-21.
 * 简单工厂，与静态工厂方法一样，它其实也不是所谓的“工厂模式”
 * 好处：
 * 把创建对象的过程，从使用对象的客户端中分离出来了，可以被多个客户端重用，并且在以后改变类的创建逻辑的时候，只需改变单独的
 * 简单工厂的代码
 * 与静态工厂方法的区别：
 * 相比而言，静态工厂的优点是不需要实例化工厂对象，缺点是不能通过继承来改变创建方法的行为
 */
public class SimpleFactory {

    public Pizza createPizza(String type){
        Pizza pizza=null;

        if(type.equals("CheesePizza")){
            pizza=new CheesePizza();
        }else if(type.equals("PepperoniPizza")){
            pizza=new PepperoniPizza();
        }else if(type.equals("ClamPizza")){
            pizza=new ClamPizza();
        }else if(type.equals("VeggiePizza")){
            pizza=new VeggiePizza();
        }

        return pizza;
    }

    public static void main(String[] args) {

        //创建对象的过程封装在工厂方法中
        Pizza pizza=new SimpleFactory().createPizza("CheesePizza");

        //其它逻辑
        pizza.bake();
        pizza.cut();
        pizza.box();

    }
}

interface Pizza{
    void bake();
    void cut();
    void box();
}

class CheesePizza implements Pizza{
    public void bake() {

    }

    public void cut() {

    }

    public void box() {

    }
}
class PepperoniPizza implements Pizza{
    public void bake() {

    }

    public void cut() {

    }

    public void box() {

    }
}
class ClamPizza implements Pizza{
    public void bake() {

    }

    public void cut() {

    }

    public void box() {

    }
}
class VeggiePizza implements Pizza{
    public void bake() {

    }

    public void cut() {

    }

    public void box() {

    }
}
