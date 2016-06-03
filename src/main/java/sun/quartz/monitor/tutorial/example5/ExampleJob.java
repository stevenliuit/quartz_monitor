package sun.quartz.monitor.tutorial.example5;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.Date;

/**
 * Created by yamorn on 2016/6/2.
 */
public class ExampleJob extends QuartzJobBean {
    private static Logger _log = LoggerFactory.getLogger(ExampleJob.class);

    private int timeout;
    /**
     * Setter called after the ExampleJob is instantiated
     * with the value from the JobDetailFactoryBean (5)
     */
    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        JobKey jobKey = context.getJobDetail().getKey();

        if (context.isRecovering()) {
            _log.info("SimpleRecoveryJob: " + jobKey + " RECOVERING at " + new Date());
        } else {
            _log.info("SimpleRecoveryJob: " + jobKey + " starting at " + new Date());
        }
        _log.info("timeout : {}", timeout);

        // delay for ten seconds
        long delay = 5 * 1000L;
        try {
            Thread.sleep(delay);
        } catch (Exception e) {
            //
        }

        _log.info("SimpleRecoveryJob: " + jobKey + " done at " + new Date() + "\n");
    }

}
