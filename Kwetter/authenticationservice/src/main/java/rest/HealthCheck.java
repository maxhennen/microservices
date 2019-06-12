package rest;

import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/health")
@Produces(MediaType.APPLICATION_JSON)
@Stateless
public class HealthCheck {

    @GET
    @Path("/check")
    public boolean check(){return true;}
}
