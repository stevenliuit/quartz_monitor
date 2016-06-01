package sun.quartz.monitor.tutorial.example1;

import org.quartz.*;

/**
 * Created by sunyamorn on 6/1/16.
 */
public class MyJob implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("hello world");

        JobKey jobKey = jobExecutionContext.getJobDetail().getKey();

//        JobDataMap dataMap = jobExecutionContext.getJobDetail().getJobDataMap();

        /*
            Note: It is a merge of the JobDataMap found on the JobDetail and the one found on the Trigger, with
            the values in the latter overriding any same-named values in the former.
         */
        JobDataMap dataMap = jobExecutionContext.getMergedJobDataMap();


        String jobSays = dataMap.getString("jobSays");

        float myFloatValue = dataMap.getFloatValue("myFloatValue");

        System.out.println(String.format("Instance %s of DumJob says: %s, and value is %f", jobKey, jobSays, myFloatValue));

    }
}
