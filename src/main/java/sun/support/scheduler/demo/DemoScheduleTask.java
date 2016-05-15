package sun.support.scheduler.demo;


import sun.support.scheduler.core.AbstractScheduleTask;
import sun.support.scheduler.core.JobContext;

import java.util.Collections;
import java.util.Map;

/**
 * Created by yamorn on 2016/5/12.
 */
public class DemoScheduleTask extends AbstractScheduleTask {
    @Override
    public boolean preCondition() {
        return true;
    }

    @Override
    public Map<String, Object> doTask(JobContext jobContext) {
        System.out.println("hello " + Thread.currentThread().getName());
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return Collections.emptyMap();
    }
}
