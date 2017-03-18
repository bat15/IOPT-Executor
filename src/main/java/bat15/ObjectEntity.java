package bat15;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by xoton on 17.03.2017.
 */
public class ObjectEntity {

    private String id;
    private String name;
    private List<PropertyEntity> properties = new LinkedList<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<PropertyEntity> getProperties() {
        return properties;
    }

    public void setProperties(List<PropertyEntity> properties) {
        this.properties = properties;
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

        properties.forEach(o ->
                b.add(o.getName(), o.getValue()));

        return b.build();
    }
}
