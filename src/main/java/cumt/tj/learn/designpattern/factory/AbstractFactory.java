package cumt.tj.learn.designpattern.factory;

/**
 * Created by sky on 17-6-21.
 * 抽象工厂模式提供一个接口，用于创建相关或依赖对象的家族，而不需要明确指定具体类
 * 参考博客：http://blog.csdn.net/jason0539/article/details/44976775
 * 产品族
 * 等级结构
 */
public interface AbstractFactory {

    void createButton();
    void createText();

}

//创建windows家族的产品
class WindowsFactory implements AbstractFactory{
    public void createButton() {

    }

    public void createText() {

    }
}

//创建unix家族的产品
class UnixFactory implements AbstractFactory{
    public void createButton() {

    }

    public void createText() {

    }
}

//两个不同的等级结构
interface Button{}

class WindowsButton implements Button{}
class UnixButton implements Button{}

interface Text{}

class WindowsText implements Text{}
class UnixText implements Text{}


