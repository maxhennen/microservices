package rest;

import authentication.Secured;
import domain.Like;
import services.KweetService;
import services.LikeService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;


@Stateless
@Path("like")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_FORM_URLENCODED})
@Secured
public class LikeAPI extends Application {

    @Inject
    private LikeService likeService;

//    @Inject
//    private UserService userService;

    @Inject
    private KweetService kweetService;

    @GET
    public Response findAll(){
        List<Like> likes = likeService.findALlLikes();
        if (likes != null && !likes.isEmpty()){
            return Response.ok().entity(likes).build();
        } else {
            return Response.noContent().build();
        }
    }

//    @POST
//    public Response save(@FormParam("email") String email, @FormParam("id") long kweetId){
//        Like like = new Like(LocalDateTime.now(), userService.findByEmail(email), kweetId);
//        if(likeService.createLike(like) != null){
//            return Response.ok().entity(like).build();
//        } else {
//            return Response.status(409).build();
//        }
//    }

    @GET
    @Path("{id}")
    public Response findOneById(@PathParam("id") long id){
        Like like = likeService.getById(id);
        if(like != null){
            return Response.ok().entity(like).build();
        } else {
            return Response.noContent().build();
        }
    }

    @DELETE
    @Path("{id}")
    public Response delete(@PathParam("id") long id){
        if(likeService.removeLike(likeService.getById(id)) != null){
            return Response.ok().entity("Like is removed").build();
        } else {
            return Response.status(409).build();
        }
    }

}