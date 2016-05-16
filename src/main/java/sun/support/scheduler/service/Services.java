package sun.support.scheduler.service;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import sun.support.scheduler.batch.JobCleaner;
import sun.support.scheduler.dao.DataAccessOperation;

/**
 * Created by yamorn on 2016/5/12.
 */
public interface Services extends InitializingBean {

    ThreadPoolTaskScheduler getThreadPoolTaskScheduler();

    DataAccessOperation getDataAccessOperation();

    JobCleaner getJobCleaner();

}
