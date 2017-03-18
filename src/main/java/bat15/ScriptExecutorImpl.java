package bat15;

import javax.enterprise.context.ApplicationScoped;
import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.logging.Logger;

/**
 * Created by xoton on 18.03.2017.
 */
@ApplicationScoped
public class ScriptExecutorImpl implements ScriptExecutor {

    private static Logger log = Logger.getLogger("scheduler");


    @Override
    public void execute(String code) throws Exception {
        log.severe("Running code : " + code);

        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("JavaScript");

        Invocable inv = (Invocable) engine;
        try {
            engine.eval(code);
            String testModelName = "modelName";
            inv.invokeFunction("run", testModelName);

        } catch (ScriptException | NoSuchMethodException e) {
            e.printStackTrace();
            throw new Exception(e);
        }

    }
}
