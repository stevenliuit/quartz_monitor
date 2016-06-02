package sun.quartz.monitor.tutorial.example3;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * Created by yamorn on 2016/6/2.
 */
public class SimpleRecoveryJob implements Job {
    private static Logger _log = LoggerFactory.getLogger(SimpleRecoveryJob.class);

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobKey jobKey = context.getJobDetail().getKey();

        if (context.isRecovering()) {
            _log.info("SimpleRecoveryJob: " + jobKey + " RECOVERING at " + new Date());
        } else {
            _log.info("SimpleRecoveryJob: " + jobKey + " starting at " + new Date());
        }

//        // delay for ten seconds
//        long delay = 10L * 1000L;
//        try {
//            Thread.sleep(delay);
//        } catch (Exception e) {
//            //
//        }

        _log.info("SimpleRecoveryJob: " + jobKey + " done at " + new Date() + "\n");
    }
}
