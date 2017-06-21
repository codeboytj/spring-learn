package cumt.tj.learn.designpattern.factory;

/**
 * Created by sky on 17-6-21.
 * 工厂模式定义了一个创建对象的接口，但由子类决定要实例化那一个类。工厂方法让类把实例化推迟到子类
 */
public abstract class Factory {

    //创建对象的逻辑由子类来完成
    public abstract Object createObject();

    public void useObject(){
        //创建对象
        Object o=createObject();

        //使用对象
        System.out.println(o.toString());
        Class c=o.getClass();
        System.out.println(c.getName());
    }

    public static void main(String[] args) {
        Factory stringFactory=new StringFactory();
        Factory integerFactory=new IntegerFactory();

        stringFactory.useObject();
        integerFactory.useObject();
    }

}

class StringFactory extends Factory{
    public Object createObject() {
        return "我是字符串工厂创建的";
    }
}

class IntegerFactory extends Factory{
    public Object createObject() {
        return 12345;
    }
}
