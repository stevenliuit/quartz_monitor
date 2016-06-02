package sun.quartz.monitor.tutorial.example1;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.KeyMatcher;
import org.quartz.impl.matchers.NameMatcher;

/**
 * Created by sunyamorn on 6/1/16.
 */
public class Hello {

    public static void main(String[] args) throws SchedulerException, InterruptedException {
        JobDetail job = JobBuilder.newJob(MyJob.class)
                .withIdentity("job1", "group1")
                .usingJobData("jobSays","Hello world")
                .usingJobData("myFloatValue", 3.14f)
                .build();
        Trigger trigger = TriggerBuilder.newTrigger().withIdentity("trigger1", "group1")
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(10).repeatForever()).build();

        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
        scheduler.getListenerManager().addJobListener(new MyJobListener(),
               KeyMatcher.keyEquals(new JobKey("job1","group1")));
        scheduler.scheduleJob(job, trigger);
        scheduler.start();

        Thread.sleep(60L * 1000L);

        scheduler.shutdown(true);


    }
}
