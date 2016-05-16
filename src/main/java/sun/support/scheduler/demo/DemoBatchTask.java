package sun.support.scheduler.demo;

import org.springframework.batch.core.JobParameters;
import sun.support.scheduler.batch.AbstractBatchScheduleTask;
import sun.support.scheduler.core.DBType;
import sun.support.scheduler.core.JobContext;

/**
 * Created by yamorn on 2016/5/13.
 */
public class DemoBatchTask extends AbstractBatchScheduleTask {

    @Override
    public JobParameters getJobParameters(JobContext jobContext) {
        return null;
    }

    @Override
    public void batchTaskDone(JobContext jobContext) {

    }


}
