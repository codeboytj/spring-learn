package cumt.tj.learn.designpattern.singleton;

/**
 * Created by sky on 17-6-16.
 * 使用Synchronized关键字修饰getInstance()方法实现的线程安全的单例模式
 */
public class SynchronizedSingleton {
    private static SynchronizedSingleton singleton;
    public int counter;

    public SynchronizedSingleton(){
        counter++;
    }

    public static synchronized SynchronizedSingleton getInstance(){
        if(singleton==null){
            singleton=new SynchronizedSingleton();
        }
        return singleton;
    }

    public void showCounter(){
//        counter++;
        System.out.println(counter);
    }

    public static class RunClass implements Runnable{
        public void run() {
            SynchronizedSingleton singleton=SynchronizedSingleton.getInstance();
            singleton.showCounter();
        }
    }

    public static void main(String[] args) {
        //创建1000个线程创建单例对象
        for(int i=0;i<1000;i++){
            new Thread(new RunClass()).start();
        }
    }
}
