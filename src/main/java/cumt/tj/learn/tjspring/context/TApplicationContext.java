package cumt.tj.learn.tjspring.context;

/**
 * Created by sky on 17-6-21.
 * 代表着IoC容器
 * 这个类负责根据元配置，实例化、组合以及初始化Beans。
 */
public interface TApplicationContext {
    <T> T getBean(Class<T> tClass);
}
