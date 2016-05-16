package sun.support.scheduler.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Created by root on 2016/3/9.
 */
public abstract class AbstractScheduleTask implements ScheduleTask {

    private static final Logger logger = LoggerFactory.getLogger(AbstractScheduleTask.class);

    private JobContext jobContext;

    protected String name;

    private TaskListener taskListener;

    private TaskContext taskContext;

    public AbstractScheduleTask() {
        taskContext = new TaskContext();
    }

    @Override
    public void setJobContext(JobContext jobContext) {
        this.jobContext = jobContext;
    }

    @Override
    public boolean preCondition(JobContext jobContext) {
        return true;
    }

    public abstract void prepare(JobContext jobContext);

    @Override
    public Map<String, Object> call() {

        Map<String, Object> retVal = null;

        if (preCondition(jobContext)) {
            taskContext.setJobContext(jobContext);
            if (taskListener != null) {
                taskListener.taskStart(taskContext);
            }

            prepare(jobContext);

            try {
                retVal = doTask(jobContext);
            } catch (Exception e) {
                taskContext.setException(e);
                logger.error(e.getMessage());
            } finally {
                // record log
                if (taskListener != null) {
                    taskListener.taskDone(taskContext);
                }
            }
        }
        return retVal;
    }

    @Override
    public void setBeanName(String name) {
        this.name = name;
    }

    public void setTaskListener(TaskListener taskListener) {
        this.taskListener = taskListener;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        //
    }

}
