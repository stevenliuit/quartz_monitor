package sun.support.scheduler.core;

import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import sun.support.scheduler.dao.DataAccessOperation;
import sun.support.scheduler.dao.OperationCallback;
import sun.support.scheduler.domain.Constant;
import sun.support.scheduler.domain.JobEntity;
import sun.support.scheduler.service.Provider;
import sun.support.scheduler.service.Services;

import java.util.*;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by sunyamorn on 3/6/16.
 * <p/>
 * Base class for all tasks.
 */
public abstract class AbstractScheduleJobDispatcher implements ScheduleJobDispatcher, BeanNameAware,
        InitializingBean, DisposableBean {

    private Logger logger = Logger.getLogger(AbstractScheduleJobDispatcher.class.getName());

    private String jobName;

    private JobContext jobContext;

    private JobListener jobListener;

    private ThreadPoolTaskScheduler executorService;

    protected List<ScheduleTask> scheduleTasks = Collections.emptyList();

    private DataAccessOperation<JobEntity, String> dataAccessOperation;

    public AbstractScheduleJobDispatcher() {
        jobContext = new JobContext();
    }

    public abstract JobType getJobType();

    @Override
    public void run() {
        // synchronization lock of distribute servers
        if (!tryLock())
            return;

        // update job status & job context
        beforeJob();

        // job launch listener to record log
        if (jobListener != null) {
            jobListener.jobStart(jobContext);
        }

        Map<String, Object> retVal = new HashMap<>();

        boolean hasException = false;
        try {
            // single thread task
            if (scheduleTasks.size() == 1) {
                ScheduleTask scheduleTask = scheduleTasks.get(0);
                scheduleTask.setJobContext(jobContext);
                retVal = scheduleTask.call();
            } else {
                Stack<Future<Map<String, Object>>> stack = new Stack<>();
                for (ScheduleTask scheduleTask : scheduleTasks) {
                    scheduleTask.setJobContext(jobContext);
                    Future<Map<String, Object>> future = Provider.getInstance()
                            .get(Constant.CONFIG_SERVICE_KEY).getThreadPoolTaskScheduler().submit(scheduleTask);
                    stack.push(future);
                }
                // wait for all tasks completed
                while (!stack.isEmpty()) {
                    Map<String, Object> taskRetVal = stack.pop().get(); // get() will block thread
                    if (taskRetVal != null) {
                        retVal.putAll(taskRetVal);
                    }
                }
            }
            // add all task result to job context;
            jobContext.setJobRetVal(retVal);
        } catch (Exception e) {
            hasException = true;
            jobContext.setException(e);
            logger.log(Level.SEVERE, e.getMessage());
        } finally {
            if (hasException) {
                jobContext.getJobEntity().setJobStatus(ScheduleStatus.FAILED.name());
            } else {
                jobContext.getJobEntity().setJobStatus(ScheduleStatus.COMPLETED.name());
            }

            // update job context & reset job status
            afterJob(hasException);

            // release job lock
            release();

            // record log
            if (jobListener != null) {
                jobListener.jobDone(jobContext);
            }
        }
    }


    @Override
    public void beforeJob() {
        updateJobContext(new Date(), false, ScheduleStatus.RUNNING);
    }

    @Override
    public void afterJob(boolean hasException) {
        updateJobContext(new Date(), false, hasException ? ScheduleStatus.FAILED : ScheduleStatus.COMPLETED);
    }

    private boolean tryLock() {
        return dataAccessOperation.pessimisticLockQuery(jobName, new OperationCallback<JobEntity, Boolean>() {
            @Override
            public Boolean process(JobEntity jobEntity) {
                if (jobEntity.getRunFlag() != JobRunFlag.HANGUP.getValue() &&
                        jobEntity.getJobLock() == EntranceGuard.RELEASE.getStatus()) {
                    if (dataAccessOperation.updateLockStatus(jobName, EntranceGuard.LOCK))
                        return true;
                } else {
                    return false;
                }
                return false;
            }
        });
    }

    private void updateJobContext(Date date, boolean isStart, ScheduleStatus scheduleStatus) {
        // maintain context in memory
        if (isStart) {
            jobContext.getJobEntity().setLastStartTime(date);
            jobContext.getJobEntity().setJobLock(EntranceGuard.LOCK.getStatus());
        } else {
            jobContext.getJobEntity().setLastStartTime(date);
            jobContext.getJobEntity().setJobLock(EntranceGuard.RELEASE.getStatus());
        }
        jobContext.getJobEntity().setJobStatus(scheduleStatus.name());

        // maintain context in db
        dataAccessOperation.updateJobStatus(jobName, scheduleStatus);
    }

    private boolean release() {
        return dataAccessOperation.updateLockStatus(jobName, EntranceGuard.RELEASE);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void afterPropertiesSet() throws Exception {
        // This attribute depends on ConfigService class
        Services services = Provider.getInstance().get(Constant.CONFIG_SERVICE_KEY);
        executorService = services.getThreadPoolTaskScheduler();
        dataAccessOperation = services.getDataAccessOperation();

        assert (dataAccessOperation != null);
        assert scheduleTasks != null && scheduleTasks.size() > 0;
        assert getJobType() != null;

        // init
        JobEntity entity = dataAccessOperation.query(jobName, false);
        if(entity == null){
            entity = new JobEntity();
            entity.setJobName(jobName);
            entity.setTasksNum(scheduleTasks.size());
            entity.setJobStatus(ScheduleStatus.WAIT.name());
            entity.setJobLock(EntranceGuard.RELEASE.getStatus());
            entity.setRunFlag(JobRunFlag.RUNNING.getValue());
            entity.setLastEndTime(null);
            entity.setLastStartTime(null);
            entity.setException(null);
            entity.setScheduleName(executorService.getThreadNamePrefix());
            entity.setJobGroup(""); // empty
            entity.setJobClassName(executorService.getClass().getName());
            entity.setJobType(getJobType().name());

            // initialize job
            if (!dataAccessOperation.insert(entity))
                logger.log(Level.SEVERE, "Initialize job record failed.");
        }

        // Reset job status
        jobContext.setJobEntity(entity);


    }

    @Override
    public void destroy() throws Exception {
        executorService.destroy();
    }

    @Override
    public void setBeanName(String name) {
        this.jobName = name;
    }

    @Override
    public void setScheduleTasks(List<ScheduleTask> scheduleTasks) {
        this.scheduleTasks = scheduleTasks;
    }

    @Override
    public void setJobListener(JobListener jobListener) {
        this.jobListener = jobListener;
    }

}
