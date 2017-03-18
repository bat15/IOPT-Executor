package bat15.restful;

import bat15.DB;
import bat15.ModelEntity;
import bat15.ScriptExecutor;

import javax.annotation.ManagedBean;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by xoton on 18.03.2017.
 */
@ManagedBean
@ApplicationScoped
@Path("execute")
public class Execute {


    @Inject
    DB db;
    @Inject
    ScriptExecutor scriptExecutor;

    @GET
    @Path("test")
    @Produces(MediaType.APPLICATION_JSON)
    public Response execute() {
        String code = "function run(name) {  }";

        try {
            scriptExecutor.execute(code);
        } catch (Exception e) {
            return Response.serverError().entity(e.getMessage()).build();
        }

        return Response.ok().build();
    }

    @GET
    @Path("{script_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response scope(@PathParam("script_id") String id) {

        ModelEntity m = db.getModelByScript(id);

        return Response.ok().entity(m.toJson()).build();
    }
}
