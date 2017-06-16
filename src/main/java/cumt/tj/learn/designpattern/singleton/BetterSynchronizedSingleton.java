package cumt.tj.learn.designpattern.singleton;

/**
 * Created by sky on 17-6-16.
 * 使用“双重检查加锁”的线程安全的单例模式，在getInstance()方法中减少了使用同步
 */
public class BetterSynchronizedSingleton {
    //并没有搞懂这个volatile是搞什么的
    private volatile static BetterSynchronizedSingleton singleton;
    public int counter;

    public BetterSynchronizedSingleton(){
        counter++;
    }

    public static BetterSynchronizedSingleton getInstance(){
        if(singleton==null){
            //单例还没有创建，才进入同步代码段
            synchronized (BetterSynchronizedSingleton.class) {
                if(singleton==null) {
                    //如果上一个线程没有创建单例，才开始创建单例
                    singleton = new BetterSynchronizedSingleton();
                }
            }
        }
        return singleton;
    }

    public void showCounter(){
//        counter++;
        System.out.println(counter);
    }

    public static class RunClass implements Runnable{
        public void run() {
            BetterSynchronizedSingleton singleton= BetterSynchronizedSingleton.getInstance();
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
