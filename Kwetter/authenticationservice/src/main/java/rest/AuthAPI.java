package rest;

import domain.AuthUser;
import tokenbean.JWToken;
import domain.Token;
import service.AuthService;
import utils.AuthenticationUtils;
import utils.LoginResponse;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.time.LocalDateTime;


@Stateless
@Path("auth")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_FORM_URLENCODED})
public class AuthAPI extends Application {

    @Inject
    private AuthService authService;

    @EJB
    private tokenbean.Token token;

    @POST
    public Response token(@FormParam("email") String email, @FormParam("password") String password){

        try {
            AuthUser user = authenticate(email, password);

            String token = this.token.createToken(user);

            authService.addToken(new Token(token, LocalDateTime.now().plusMinutes(60)));

            return Response.ok(new LoginResponse(token, user)).build();
        } catch (Exception e){
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

    }

    private AuthUser authenticate(String email, String password){
        String passwordEncoded = AuthenticationUtils.encodeSHA256(password);
        AuthUser user = authService.login(email, passwordEncoded);

        if(user == null){
            throw new SecurityException("Invalid email or password");
        }

        return user;
    }

    @POST
    @Path("logout")
    public Response logout(@FormParam("email") String email) {
        try {
//            SessionListener.getInstance().getActiveUsers().remove(email);
            return Response.ok(email).build();
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @POST
    @Path("create")
    public Response createUser(@FormParam("email") String email, @FormParam("password") String password){

        AuthUser user = authService.register(email, password);

        if(user != null){
//            try {
//                emailSender.sendMail(user);
//            } catch (MessagingException e) {
//                e.printStackTrace();
//            } catch (UnsupportedEncodingException e) {
//                e.printStackTrace();
//            }
            return Response.ok().entity(user).build();
        } else {
            return Response.status(409).build();
        }
    }

    @POST
    @Path("checktoken")
    public Response checkToken(@FormParam("token") String token){
        boolean tokenCheck =  authService.checkToken(token);

        if(tokenCheck){
            return Response.ok(token).build();
        } else {
            return Response.status(401).build();
        }
    }
}
