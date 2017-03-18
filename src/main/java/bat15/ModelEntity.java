package bat15;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by xoton on 17.03.2017.
 */
public class ModelEntity {
    private String id;
    private String name;
    private List<ObjectEntity> objects = new LinkedList<>();

    public List<ObjectEntity> getObjects() {
        return objects;
    }

    public void setObjects(List<ObjectEntity> objects) {
        this.objects = objects;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public JsonObject toJson() {
        JsonObjectBuilder b = Json.createObjectBuilder();
        b.add("_id", id);
        b.add("_name", name);

        //JsonArrayBuilder ao = Json.createArrayBuilder();
        objects.forEach(o ->
                b.add(o.getName(), o.toJson()));

        return Json.createObjectBuilder().add("model", b.build()).build();
    }
}
