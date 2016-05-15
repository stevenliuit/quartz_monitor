package sun.support.scheduler.dao;


import sun.support.scheduler.core.EntranceGuard;
import sun.support.scheduler.core.JobType;
import sun.support.scheduler.core.ScheduleStatus;

import java.util.List;

/**
 * Created by yamorn on 2016/5/12.
 */
public interface DataAccessOperation<T, V> {

    T query(V id, boolean lock);

    List<T> queryAll();

    List<T> query(JobType jobType);

    boolean insert(T entity);

    boolean updateLockStatus(V id, EntranceGuard lockStatus);

    boolean updateJobStatus(V id, ScheduleStatus scheduleStatus);

    boolean pessimisticLockQuery(final V id, OperationCallback<T, Boolean> callback);

    boolean releaseJobLock(V id);

    boolean hangup(V id, int flag);

    void log(T entity);


}
