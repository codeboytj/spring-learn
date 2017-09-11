package cumt.tj.learn.spring.tx;

/**
 * Created by sky on 17-9-11.
 */
public interface FooService {

    Foo getFoo(String fooName);

    Foo getFoo(String fooName, String barName);

    void insertFoo(Foo foo);

    void updateFoo(Foo foo);

}

class Foo{

}
