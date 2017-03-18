package bat15;

import javax.json.Json;
import javax.json.JsonObject;

/**
 * Created by xoton on 19.03.2017.
 */
public class JsonUtils {

    public static JsonObject ok() {
        return Json.createObjectBuilder().add("ok", "true").build();
    }

    public static JsonObject fail(String cause) {
        return Json.createObjectBuilder()
                .add("ok", "false")
                .add("description", cause)
                .build();
    }
}
