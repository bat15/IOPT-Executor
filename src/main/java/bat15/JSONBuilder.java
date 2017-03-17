package bat15;

import javax.json.Json;
import javax.json.JsonObjectBuilder;

/**
 * Created by xoton on 17.03.2017.
 */
public class JSONBuilder {

    public static String getJSON(ModelEntity model) {

        JsonObjectBuilder o = Json.createObjectBuilder();


        return Json.createObjectBuilder()
                .add(model.getName(), "test").build().toString();
    }

}
