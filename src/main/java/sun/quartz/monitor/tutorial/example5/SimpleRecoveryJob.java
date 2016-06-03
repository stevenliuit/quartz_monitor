package sun.quartz.monitor.tutorial.example5;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by yamorn on 2016/6/2.
 */
@Service
public class SimpleRecoveryJob extends QuartzJobBean {
    private static Logger _log = LoggerFactory.getLogger(SimpleRecoveryJob.class);

    @Autowired
    private SimpleHello simpleHello;


    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        JobKey jobKey = context.getJobDetail().getKey();

        _log.info("say -> {}", simpleHello.hello());
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
