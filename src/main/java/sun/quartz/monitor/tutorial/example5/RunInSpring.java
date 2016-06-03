package sun.quartz.monitor.tutorial.example5;

import org.quartz.Scheduler;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by yamorn on 2016/6/3.
 */
public class RunInSpring {
    public static void main(String[] args) throws Exception{

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring-context.xml");
        Scheduler scheduler = applicationContext.getBean(Scheduler.class);

        scheduler.start();
    }
}
