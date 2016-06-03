package sun.quartz.monitor.tutorial.example5;

import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import sun.quartz.monitor.tutorial.example4.SimpleRecoveryJob;

/**
 * Created by sunyamorn on 6/3/16.
 */
public class RunInSpring {
    static Logger _log = LoggerFactory.getLogger(RunInSpring.class);


    public static void main(String[] args) throws Exception {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring-context.xml");
        Scheduler scheduler = applicationContext.getBean(Scheduler.class);

//        scheduler.clear();
        scheduler.start();



    }
}
