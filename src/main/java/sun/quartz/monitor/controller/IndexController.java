package sun.quartz.monitor.controller;

import org.quartz.*;
import org.quartz.impl.StdScheduler;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import sun.quartz.monitor.component.SchedulerMonitor;

import java.util.Date;
import java.util.List;

/**
 * Created by yamorn on 2016/6/4.
 */
@Controller
@RequestMapping("/")
public class IndexController {

    @Autowired
    private SchedulerMonitor monitor;

    @RequestMapping("index")
    public String index() throws Exception{


        return "index";
    }

    @RequestMapping(value = "getJobData", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public String getJobData(){
        return monitor.jobListToJson();
    }

    @RequestMapping(value = "getJobTriggers", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public String getJobTriggers(@RequestParam String jobName, @RequestParam String jobGroup){
        return monitor.jobTriggerToJson(monitor.getTriggers(jobName, jobGroup));
    }
}
