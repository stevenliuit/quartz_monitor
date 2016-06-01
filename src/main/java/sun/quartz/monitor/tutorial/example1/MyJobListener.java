package sun.quartz.monitor.tutorial.example1;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.listeners.JobListenerSupport;

/**
 * Created by sunyamorn on 6/2/16.
 */
public class MyJobListener extends JobListenerSupport {
    @Override
    public String getName() {
        return "job1";
    }

    @Override
    public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
        System.out.println("jobExecuted.....");
    }
}
