package sun.support.scheduler.core;

/**
 * Created by yamorn on 2016/5/12.
 */
public interface TaskListener {

    void taskStart(TaskContext context);

    void taskDone(TaskContext context);

}
