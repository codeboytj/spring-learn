package cumt.tj.learn.spring.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.DeclareParents;
import org.omg.Messaging.SYNC_WITH_TRANSPORT;

/**
 * Created by sky on 17-8-3.
 */

@Aspect
public class UsageTracking {

    @DeclareParents(value="com.xzy.myapp.service.*+", defaultImpl=DefaultUsageTracked.class)
    public static UsageTracked mixin;

    @Before("SystemArchitecture.businessService() && this(usageTracked)")
    public void recordUsage(UsageTracked usageTracked) {
        usageTracked.incrementUseCount();
    }

}

interface UsageTracked{
    void incrementUseCount();
}

class DefaultUsageTracked implements UsageTracked{
    public void incrementUseCount() {
        System.out.println("无情增加UseCount");
    }
}
