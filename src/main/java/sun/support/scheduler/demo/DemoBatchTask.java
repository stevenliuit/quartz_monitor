package sun.support.scheduler.demo;

import org.springframework.batch.core.JobParameters;
import sun.support.scheduler.batch.AbstractBatchScheduleTask;
import sun.support.scheduler.core.DBType;

/**
 * Created by yamorn on 2016/5/13.
 */
public class DemoBatchTask extends AbstractBatchScheduleTask {
    @Override
    public void setJobParameters(JobParameters jobParameters) {

    }

    @Override
    public JobParameters getJobParameters() {
        return null;
    }

    @Override
    public DBType getDBType() {
        return null;
    }
}
