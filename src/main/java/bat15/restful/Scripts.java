package bat15.restful;

import bat15.DB;
import bat15.JsonUtils;
import bat15.ScriptEntity;

import javax.annotation.ManagedBean;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.*;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.StringReader;
import java.util.List;

/**
 * Created by xoton on 18.03.2017.
 */
@ManagedBean
@ApplicationScoped
@Path("scripts")
public class Scripts {

    @Inject
    DB db;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getScripts() {
        JsonArrayBuilder jab = Json.createArrayBuilder();

        List<ScriptEntity> entities = db.getScripts();

        entities.forEach(s -> {

            JsonObject script = Json.createObjectBuilder()
                    .add("id", s.getId())
                    .add("name", s.getName())
                    .add("id_property", s.getIdProperty())
                    .add("value", s.getValue())
                    .build();
            jab.add(script);
        });

        return Response.ok().entity(jab.build()).build();
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response saveScript(@PathParam("id") String id, String value) {
        System.out.println("Script value = " + value);

        try {
            JsonReader jsonReader = Json.createReader(new StringReader(value));
            JsonObject object = jsonReader.readObject();
            jsonReader.close();

            db.saveScript(id, object.getString("value"));
            return Response.ok().entity(JsonUtils.ok()).build();
        } catch (JsonException je) {
            je.printStackTrace();
            return Response.status(Response.Status.NOT_MODIFIED)
                    .entity(JsonUtils.fail("Bad JSON"))
                    .build();
        }
    }


}
