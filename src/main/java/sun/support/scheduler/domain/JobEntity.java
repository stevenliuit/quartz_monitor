package sun.support.scheduler.domain;

import java.util.Date;

/**
 * Created by sunyamorn on 3/6/16.
 */
public class JobEntity {

    private String jobName;
    private String jobGroup;
    private String jobStatus;
    private int jobLock;
    private int runFlag;
    private int tasksNum;
    private String jobClassName;
    private String scheduleName;
    private Date lastStartTime;
    private Date lastEndTime;
    private Exception exception;
    private long cost;
    private String jobType;

    public long getCost() {
        return cost;
    }

    public void setCost(long cost) {
        this.cost = cost;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getJobGroup() {
        return jobGroup;
    }

    public void setJobGroup(String jobGroup) {
        this.jobGroup = jobGroup;
    }

    public String getJobStatus() {
        return jobStatus;
    }

    public void setJobStatus(String jobStatus) {
        this.jobStatus = jobStatus;
    }

    public int getJobLock() {
        return jobLock;
    }

    public void setJobLock(int jobLock) {
        this.jobLock = jobLock;
    }

    public int getRunFlag() {
        return runFlag;
    }

    public void setRunFlag(int runFlag) {
        this.runFlag = runFlag;
    }

    public String getJobClassName() {
        return jobClassName;
    }

    public void setJobClassName(String jobClassName) {
        this.jobClassName = jobClassName;
    }

    public String getScheduleName() {
        return scheduleName;
    }

    public void setScheduleName(String scheduleName) {
        this.scheduleName = scheduleName;
    }

    public Date getLastStartTime() {
        return lastStartTime;
    }

    public void setLastStartTime(Date lastStartTime) {
        this.lastStartTime = lastStartTime;
    }

    public Date getLastEndTime() {
        return lastEndTime;
    }

    public void setLastEndTime(Date lastEndTime) {
        this.lastEndTime = lastEndTime;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }

    public int getTasksNum() {
        return tasksNum;
    }

    public void setTasksNum(int tasksNum) {
        this.tasksNum = tasksNum;
    }

    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }


}
