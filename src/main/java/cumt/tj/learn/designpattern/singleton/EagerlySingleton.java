package cumt.tj.learn.designpattern.singleton;

/**
 * Created by sky on 17-6-16.
 * 最简单的线程安全的单例模式，单例在定义的时候实例化，是一种“急切”的单例模式
 */
public class EagerlySingleton{

    private static EagerlySingleton singleton=new EagerlySingleton();
    public int counter;

    public static EagerlySingleton getInstance(){
        return singleton;
    }

    public void showCounter(){
        counter++;
        System.out.println(counter);
    }

    public static class RunClass implements Runnable{
        public void run() {
            EagerlySingleton singleton=EagerlySingleton.getInstance();
            singleton.showCounter();
        }
    }

    public static void main(String[] args) {
        //创建1000个线程创建单例对象
        for (int i = 0; i < 1000; i++) {
            new Thread(new RunClass()).start();
        }
        //但是，为什么打印出来的最大数还是不到1000?
    }
}
