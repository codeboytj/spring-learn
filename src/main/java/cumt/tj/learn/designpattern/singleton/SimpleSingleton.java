package cumt.tj.learn.designpattern.singleton;

/**
 * Created by sky on 17-6-16.
 * 简单的单例模式，线程不安全
 */
public class SimpleSingleton{

    private static SimpleSingleton singleton;
    public static int counter=0;

    public SimpleSingleton() {
        counter++;
        if(counter!=1){
            System.out.println("线程不安全，对counter进行了2次自增，说明调用了2次构造函数");
        }
    }

    public static SimpleSingleton getInstance() throws InterruptedException {
        if(singleton==null){
            Thread.sleep(30);
            singleton=new SimpleSingleton();
        }
        return singleton;
    }

    public static class RunClass implements Runnable{
        public void run() {
            try {
                SimpleSingleton singleton=SimpleSingleton.getInstance();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
//            singleton.showCounter();
        }
    }

    public static void main(String[] args) {
        //创建1000个线程创建单例对象
        for(int i=0;i<1000;i++){
            new Thread(new RunClass()).start();
        }
        //按照道理说，应该会出现counter!=2的情况，但是却没有出现，简直无情
    }

}
