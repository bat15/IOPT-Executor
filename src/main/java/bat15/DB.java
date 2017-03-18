package bat15;

import java.util.List;


public interface DB {

    List<ScriptEntity> getScripts();

    boolean saveScript(String id, String value);

    ModelEntity getModelByScript(String id);

    void saveModel(ModelEntity model);
}
