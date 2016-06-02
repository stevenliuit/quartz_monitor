package sun.quartz.monitor.tutorial.example3;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by yamorn on 2016/6/2.
 */
public class ClusterExample {
    private static Logger _log = LoggerFactory.getLogger(ClusterExample.class);

    public void run(boolean inClearJobs) throws Exception{

        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

        if(inClearJobs){
            _log.warn("***** Deleting existing jobs/triggers *****");
            scheduler.clear();
        }

        _log.info("------- Initialization Complete -----------");

        String schedulerId = scheduler.getSchedulerInstanceId();
        _log.info("Scheduler id is {}", schedulerId);


        JobDetail job = JobBuilder.newJob(SimpleRecoveryJob.class)
                .withIdentity("job1","group1")
                .requestRecovery()
                .build();

        // run every 5 seconds
        CronTrigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("trigger1","group1")
                .withSchedule(CronScheduleBuilder.cronSchedule("0/5 * * * * ?"))
                .build();

        scheduler.scheduleJob(job, trigger);

        _log.info("------- Starting Scheduler ---------------");
        scheduler.start();


        Thread.sleep(20L * 1000L);

        scheduler.pauseAll();
        _log.info("pauseAll for five seconds");
        Thread.sleep(5L * 1000);
        _log.info("wake up all job.");
        scheduler.resumeAll();

//        Thread.sleep(10L * 1000);
//        _log.info("delete job1");
//        scheduler.deleteJob(new JobKey("job1", "group1"));
//
//        Thread.sleep(20L * 1000L);
//        _log.info("shutdown scheduler.");
//        scheduler.shutdown(true);

    }

    public static void main(String[] args) throws Exception {
        ClusterExample example = new ClusterExample();
        example.run(true);
    }
}
