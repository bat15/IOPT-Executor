package bat15;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.logging.Logger;


public class MyScheduler implements  Job {

    private static Logger log = Logger.getLogger("scheduler");


    @Override
    public void execute(JobExecutionContext context)
            throws JobExecutionException {

        log.severe("This is test task and it must run every 5 seconds");


        DB db = new DB();

        String script = db.getFirstScript();

        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("JavaScript");

        Invocable inv = (Invocable) engine;
        try {
            engine.eval(script);
            String testModelName = "modelName";
            inv.invokeFunction("run", testModelName);

        } catch (ScriptException | NoSuchMethodException e) {
            e.printStackTrace();
        }


        log.severe("Script from database: " + script);
    }
}