package rest;

import authentication.Secured;
import domain.Details;
import domain.Location;
import domain.User;
import services.UserService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;


@Stateless
@Path("user")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_FORM_URLENCODED})
@Secured
public class UserAPI extends Application {

    @Inject
    private UserService userService;

    @GET
    public Response findAll(){
        List<User> users = userService.getAllUsers();
        if(users != null){
            return Response.ok(users).build();
        } else {
            return Response.noContent().build();
        }
    }

    @DELETE
    @Path("{email}")
    public Response delete(@PathParam("email") String email){
        User user = userService.findByEmail(email);
        if(userService.removeUser(user)){
            return Response.ok().entity("User is removed").build();
        } else {
            return Response.status(409).build();
        }
    }

    @GET
    @Path("findbyemail/{email}")
    public Response findByEmail(@PathParam("email") String email){
        User user = userService.findByEmail(email);
        if(user != null){
            user.setFollowers(userService.getFollowers(user));
            user.setFollowings(userService.getFollowing(user));
            return Response.ok(user).build();
        } else {
            return Response.noContent().build();
        }
    }

    @GET
    @Path("following/{email}")
    public Response following(@PathParam("email") String email){
        User user = userService.findByEmail(email);
        List<User> followings = userService.getFollowing(user);
        if(followings != null && !followings.isEmpty()){
            return Response.ok().entity(followings).build();
        } else {
            return Response.noContent().build();
        }
    }

    @GET
    @Path("followers/{email}")
    public Response followers(@PathParam("email") String email){
        User user = userService.findByEmail(email);
        List<User> followers =  userService.getFollowers(user);
        if(followers != null && !followers.isEmpty()){
            return Response.ok().entity(followers).build();
        } else {
            return Response.noContent().build();
        }
    }

    @DELETE
    public Response unfollow(@FormParam("follower") String emailFollower, @FormParam("following") String emailFollowing){
        if(userService.removeFollowing(userService.getFollowerByEmail(emailFollower), userService.getFollowingByEmail(emailFollowing))){
            return Response.ok().entity("Following is removed").build();
        } else {
            return Response.noContent().build();
        }
    }

    @POST
    public Response addFollower(@FormParam("follower") String emailFollower, @FormParam("following") String emailFollowing){
        User follower = userService.findByEmail(emailFollower);
        User following = userService.findByEmail(emailFollowing);
        if(userService.followUser(follower, following) != null){
            return Response.ok().entity(follower + " is now following " + following).build();
        } else {
            return Response.status(409).build();
        }
    }

    @POST
    @Path("changedetails")
    public Response changeDetails(@FormParam("bio") String bio, @FormParam("website") String website, @FormParam("email") String email){
        Details details = new Details(bio, website);
        if(userService.editDetails(email, details) != null){
            return Response.ok().entity(details).build();
        } else {
            return Response.status(409).build();
        }
    }

    @POST
    @Path("changelocation")
    public Response changeLocation(@FormParam("country") String country, @FormParam("city") String city, @FormParam("Street") String street, @FormParam("housenumber") String housenumber, @FormParam("email") String email){
        Location location = new Location(country, city, street, housenumber);
        if(userService.editLocation(email, location) != null){
            return Response.ok().entity(location).build();
        } else {
            return Response.status(409).build();
        }
    }

    @POST
    @Path("edituser")
    public Response editUser(User user) {
        if (userService.editUser(user) != null) {
            return Response.ok(user).build();
        } else {
            return Response.status(409).build();
        }
    }
}
