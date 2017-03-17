package bat15;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xoton on 17.03.2017.
 */
public class ObjectEntity {

    private String name;
    private List<PropertyEntity> properties = new ArrayList<>();

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
}
