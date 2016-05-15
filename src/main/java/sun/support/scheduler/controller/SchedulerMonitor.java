package sun.support.scheduler.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import sun.support.scheduler.core.JobType;
import sun.support.scheduler.dao.DataAccessOperation;
import sun.support.scheduler.domain.JobEntity;

import java.util.*;

/**
 * Created by root on 2016/3/7.
 */
@Controller
@RequestMapping("/")
public class SchedulerMonitor {

    @Autowired
    private DataAccessOperation<JobEntity, String> dataAccessOperation;

    @RequestMapping("/index")
    public String index() {
        return "index";
    }

    @RequestMapping(value = "job", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public
    @ResponseBody
    List<JobEntity> queryJobInfo(String jobType) {
        List<JobEntity> list = dataAccessOperation.query(JobType.valueOf(jobType));
        if (list == null)
            list = new ArrayList<>();
        for (JobEntity jobEntity : list) {
            jobEntity.setCost(jobEntity.getLastEndTime().getTime() - jobEntity.getLastStartTime().getTime());
        }
        return list;
    }

    @RequestMapping(value = "release", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public
    @ResponseBody
    Map<String, Boolean> releaseLock(@RequestParam String id) {
        boolean result = dataAccessOperation.releaseJobLock(id);
        Map<String, Boolean> map = new HashMap<>();
        map.put("status", result);
        return map;
    }


    @RequestMapping(value = "hangup", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public
    @ResponseBody
    Map<String, Boolean> hangup(@RequestParam String id, @RequestParam int flag) {
        boolean result = dataAccessOperation.hangup(id, flag);
        Map<String, Boolean> map = new HashMap<>();
        map.put("status", result);
        return map;
    }
}
