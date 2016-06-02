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

        // run every 20 seconds
        CronTrigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("trigger1","group1")
                .withSchedule(CronScheduleBuilder.cronSchedule("0/20 * * * * ?"))
                .build();

        scheduler.scheduleJob(job, trigger);

        _log.info("------- Starting Scheduler ---------------");
        scheduler.start();

    }

    public static void main(String[] args) throws Exception {
        ClusterExample example = new ClusterExample();
        example.run(false);
    }
}
