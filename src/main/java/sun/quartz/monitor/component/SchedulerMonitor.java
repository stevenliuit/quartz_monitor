package sun.quartz.monitor.component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.org.apache.bcel.internal.generic.LNEG;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;

import java.util.*;

/**
 * Created by sunyamorn on 6/5/16.
 */
public final class SchedulerMonitor {

    public SchedulerMonitor() {
    }

    public SchedulerMonitor(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    private Scheduler scheduler;

    public Scheduler getScheduler() {
        return scheduler;
    }

    public void setScheduler(Scheduler scheduler) {
        this.scheduler = scheduler;
    }


    private ObjectMapper mapper = new ObjectMapper();

    /**
     * Get all jobDetails
     *
     * @return all jobDetails
     * @throws Exception
     */
    public List<JobDetail> jobList() throws Exception {
        List<JobDetail> jobDetails = new LinkedList<>();
        for (String groupName : scheduler.getJobGroupNames()) {
            for (JobKey jobKey : scheduler.getJobKeys(GroupMatcher.jobGroupEquals(groupName))) {
                JobDetail jobDetail = scheduler.getJobDetail(jobKey);
                jobDetails.add(jobDetail);
            }
        }
        return jobDetails;
    }

    public String jobListToJson() {

        List<JobDetail> list = null;
        List<Map<String, Object>> data = new LinkedList<>();
        try {
            list = jobList();
            for (JobDetail jobDetail : list) {
                Map<String, Object> map = new HashMap<>();
                map.put("name", jobDetail.getKey().getName());
                map.put("group", jobDetail.getKey().getGroup());
                map.put("jobClass", jobDetail.getJobClass().getName());
                map.put("jobDataMap", getJobDataMap(jobDetail));
                map.put("durable", jobDetail.isDurable());
                map.put("requestRecovery", jobDetail.requestsRecovery());
                map.put("concurrent", jobDetail.isConcurrentExectionDisallowed());
                map.put("persist", jobDetail.isPersistJobDataAfterExecution());
                map.put("description", jobDetail.getDescription());
                data.add(map);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
        Map<String, Object> result = new HashMap<>();
        result.put("data", data);
        try {
            return mapper.writeValueAsString(result);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "";
    }

    private Map<String, Object> getJobDataMap(JobDetail jobDetail) {
        Map<String, Object> map = new HashMap<>();
        JobDataMap dataMap = jobDetail.getJobDataMap();
        for (String key : dataMap.keySet()) {
            Object value = dataMap.get(key);
            map.put(key, value);
        }
        return map;
    }

    public List<? extends Trigger> getTriggers(String jobName, String jobGroup) {
        List<? extends Trigger> list = new LinkedList<>();
        JobKey jobKey = new JobKey(jobName, jobGroup);
        try {
            list = scheduler.getTriggersOfJob(jobKey);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return list;
    }

    public String jobTriggerToJson(List<? extends Trigger> triggers) {
        if (triggers == null || triggers.size() == 0)
            return "";
        Map<String, Object> map = new HashMap<>();
        for (Trigger trigger : triggers) {
            if (trigger instanceof CronTrigger) {
                CronTrigger t = (CronTrigger) trigger;
                map.put("type", "CronTrigger");
                map.put("expression", t.getCronExpression());
                map.put("name", t.getKey().getName());
                map.put("group", t.getKey().getGroup());
                map.put("nextFireTime", DateFormatUtils.format(t.getNextFireTime(), "yyyy-MM-dd HH:mm:ss"));
            }
        }
        try {
            return mapper.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "";
    }


}
