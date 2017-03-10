package bat15;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.logging.Logger;

/**
 * Created by xoton on 11.03.2017.
 */
public class MyScheduler implements  Job {

    private static Logger log = Logger.getLogger("scheduler");

    @Override
    public void execute(JobExecutionContext context)
            throws JobExecutionException {

        log.severe("This is test task and it must run every 5 seconds");

        log.severe("Script from database: "  + DB.getFirstScript());
    }
}