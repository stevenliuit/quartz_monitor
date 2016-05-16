package sun.support.scheduler.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import sun.support.scheduler.core.AbstractScheduleTask;
import sun.support.scheduler.core.JobContext;
import sun.support.scheduler.domain.Constant;
import sun.support.scheduler.service.ConfigService;
import sun.support.scheduler.service.Provider;

import java.util.Collections;
import java.util.Map;

/**
 * Created by root on 2016/3/9.
 */
public abstract class AbstractBatchScheduleTask extends AbstractScheduleTask {

    protected Job job;

    protected JobLauncher jobLauncher;

    public void setJob(Job job) {
        this.job = job;
    }

    public void setJobLauncher(JobLauncher jobLauncher) {
        this.jobLauncher = jobLauncher;
    }

    public abstract JobParameters getJobParameters(JobContext jobContext);

    public abstract void batchTaskDone(JobContext jobContext);

    @Override
    public boolean preCondition(JobContext jobContext) {
        return true;
    }

    @Override
    public void prepare(JobContext jobContext) {
        ConfigService configService = (ConfigService)Provider.getInstance().get(Constant.CONFIG_SERVICE_KEY);
        configService.getJobCleaner().cleanBeforeJobLaunch(this.job.getName());
    }

    @Override
    public Map<String, Object> doTask(JobContext jobContext) {

        JobExecution jobExecution;
        try {
            jobExecution = jobLauncher.run(job, getJobParameters(jobContext));
            while (jobExecution.isRunning()) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } catch (JobExecutionAlreadyRunningException | JobRestartException |
                JobInstanceAlreadyCompleteException | JobParametersInvalidException e) {
            jobContext.setException(e);
            e.printStackTrace();
        } finally {
            batchTaskDone(jobContext);
        }
        return Collections.emptyMap();
    }
}
