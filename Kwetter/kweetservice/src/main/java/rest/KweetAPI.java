package rest;

import authentication.Secured;
import domain.Kweet;
import services.KweetService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.time.LocalDateTime;
import java.util.List;


@Stateless
@Path("kwetter")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_FORM_URLENCODED})
@Secured
public class KweetAPI extends Application {

    @Inject
    private KweetService kweetService;

    @GET
    public Response findAll(@Context UriInfo uriInfo){
        List<Kweet> kweets = kweetService.getAllKweets();
        return Response.ok(kweets).build();
    }

    @POST
    public Response save(@FormParam("content") String content, @FormParam("email") String email ){
        Kweet kweet = new Kweet(LocalDateTime.now(), content, email);

        if(kweetService.createKweet(kweet) != null){
            return Response.ok().entity(kweet).build();
        } else {
            return Response.status(409).build();
        }
    }

    @GET
    @Path("id/{id}")
    public Response findById(@PathParam("id") long id, @Context UriInfo uriInfo){
        Kweet kweet = kweetService.getKweetById(id);
        kweet.addLink(getUriForSelf(uriInfo, kweet), "GET");
        kweet.addLink(getUriForLikes(uriInfo, kweet), "GET");
        kweet.addLink(getUriForKweets(uriInfo, kweet), "GET");
        kweet.addLink(postNewKweet(uriInfo, kweet), "POST");
        kweet.addLink(deleteKweet(uriInfo, kweet), "DELETE");
        return Response.ok().entity(kweet).build();
    }

    @DELETE
    @Path("{id}")
    public Response delete(@PathParam("id") long id){
        Kweet kweet = kweetService.getKweetById(id);
        if(kweetService.removeKweet(kweet)){
            return Response.ok("Kweet is removed.").build();
        } else {
            return Response.status(409).build();
        }
    }

    @GET
    @Path("findbyemail/{email}")
    public Response findByEmail(@Context UriInfo uriInfo, @PathParam("email") String email){
        List<Kweet> kweets = kweetService.getKweetsByEmail(email);
        if(kweets != null && !kweets.isEmpty()){
            return Response.ok(kweets).build();
        } else {
            return Response.noContent().build();
        }
    }

    private String getUriForSelf(UriInfo uriInfo, Kweet kweet){
        return uriInfo.getBaseUriBuilder()
                .path(KweetAPI.class)
                .path(Long.toString(kweet.getId()))
                .build()
                .toString();
    }


    private String getUriForLikes(UriInfo uriInfo, Kweet kweet){
        return uriInfo.getBaseUriBuilder()
                .path(LikeAPI.class)
                .path(Long.toString(kweet.getId()))
                .build().toString();
    }

    private String getUriForKweets(UriInfo uriInfo, Kweet kweet){
        return uriInfo.getBaseUriBuilder()
                .path(KweetAPI.class)
                .path("findall")
                .build().toString();
    }

    private String postNewKweet(UriInfo uriInfo, Kweet kweet){
        return uriInfo.getBaseUriBuilder()
                .path(KweetAPI.class)
                .build().toString();
    }

    private String deleteKweet(UriInfo uriInfo, Kweet kweet){
        return uriInfo.getBaseUriBuilder()
                .path(KweetAPI.class)
                .path(Long.toString(kweet.getId()))
                .build().toString();
    }
}
