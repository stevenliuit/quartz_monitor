package sun.support.scheduler.core;

import sun.support.scheduler.domain.JobEntity;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by yamorn on 2016/5/12.
 */
public final class JobContext {

    private Map<String, Object> jobRetVal = new HashMap<>();
    private Exception exception = null;
    private JobEntity jobEntity = new JobEntity();

    public Map<String, Object> getJobRetVal() {
        return jobRetVal;
    }

    public void setJobRetVal(Map<String, Object> jobRetVal) {
        this.jobRetVal = jobRetVal;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }

    public JobEntity getJobEntity() {
        return jobEntity;
    }

    public void setJobEntity(JobEntity jobEntity) {
        this.jobEntity = jobEntity;
    }
}
