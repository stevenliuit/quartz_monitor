package sun.support.scheduler.service;

import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import sun.support.scheduler.batch.JobCleaner;
import sun.support.scheduler.dao.DataAccessOperation;
import sun.support.scheduler.domain.Constant;
import sun.support.scheduler.domain.JobEntity;


/**
 * Created by yamorn on 2016/5/12.
 */
public class ConfigService implements Services {

    private ThreadPoolTaskScheduler executorService;

    private DataAccessOperation<JobEntity, String> dataAccessOperation;

    private JobCleaner jobCleaner;

    @Override
    public ThreadPoolTaskScheduler getThreadPoolTaskScheduler() {
        return executorService;
    }

    @Override
    public DataAccessOperation<JobEntity, String> getDataAccessOperation() {
        return dataAccessOperation;
    }

    public ThreadPoolTaskScheduler getExecutorService() {
        return executorService;
    }

    public void setExecutorService(ThreadPoolTaskScheduler executorService) {
        this.executorService = executorService;
    }

    public void setDataAccessOperation(DataAccessOperation<JobEntity, String> dataAccessOperation) {
        this.dataAccessOperation = dataAccessOperation;
    }

    @Override
    public JobCleaner getJobCleaner() {
        return jobCleaner;
    }

    public void setJobCleaner(JobCleaner jobCleaner) {
        this.jobCleaner = jobCleaner;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Provider.getInstance().register(Constant.CONFIG_SERVICE_KEY, this);
    }
}
