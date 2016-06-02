package sun.quartz.monitor.tutorial.example4;

import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import sun.quartz.monitor.tutorial.example3.*;

/**
 * Created by sunyamorn on 6/3/16.
 */
public class RunInSpring {
    static Logger _log = LoggerFactory.getLogger(RunInSpring.class);


    public static void main(String[] args) throws Exception {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring-context.xml");
        Scheduler scheduler = applicationContext.getBean(Scheduler.class);

        String schedulerInstanceId = scheduler.getSchedulerInstanceId();

        String schedulerInstanceName = scheduler.getSchedulerName();

        _log.info("id {}, name {}", schedulerInstanceId, schedulerInstanceName);


        JobDetail job = JobBuilder.newJob(SimpleRecoveryJob.class)
                .withIdentity("job1", "group1")
                .requestRecovery()
                .build();

        // run every 5 seconds
        CronTrigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("trigger1", "group1")
                .withSchedule(CronScheduleBuilder.cronSchedule("0/5 * * * * ?"))
                .build();

        scheduler.clear();

        scheduler.scheduleJob(job, trigger);

        scheduler.start();

        _log.info("------- Starting Scheduler ---------------");
        scheduler.start();


        Thread.sleep(20L * 1000L);

        scheduler.pauseAll();
        _log.info("pauseAll for five seconds");
        Thread.sleep(5L * 1000);
        _log.info("wake up all job.");
        scheduler.resumeAll();

        Thread.sleep(10L * 1000);
        _log.info("delete job1");
        scheduler.deleteJob(new JobKey("job1", "group1"));

        Thread.sleep(20L * 1000L);
        _log.info("shutdown scheduler.");
        scheduler.shutdown(true);



    }
}
