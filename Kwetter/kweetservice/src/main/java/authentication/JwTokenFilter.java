package authentication;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.impl.DefaultClaims;
import io.jsonwebtoken.impl.DefaultHeader;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import utils.HTTPRequest;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Secured
@Provider
@Priority(Priorities.AUTHENTICATION)
public class JwTokenFilter implements ContainerRequestFilter {

    private static final String REALM = "realm";
    private static final String AUTHENTICATION = "Bearer";


    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

        if(!isTokenBasedAuthentication(authorizationHeader)){
            abortWithUnauthorized(requestContext);
            return;
        }

        String token = authorizationHeader
                .substring(AUTHENTICATION.length()).trim();
        try {
            validateToken(token, requestContext);
        } catch (Exception e){
            e.printStackTrace();
            abortWithUnauthorized(requestContext);
        }
    }

    private boolean isTokenBasedAuthentication(String authorizationHeader){
        return authorizationHeader != null && authorizationHeader.toLowerCase().startsWith(AUTHENTICATION.toLowerCase() + " ");
    }

    private void abortWithUnauthorized(ContainerRequestContext requestContext){
        requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).header(HttpHeaders.WWW_AUTHENTICATE,
                AUTHENTICATION + " realm=\"" + REALM + "\"").build());
    }

    private void validateToken(String token, ContainerRequestContext requestContext){
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("token", token));
        HttpResponse response = HTTPRequest.post("http://localhost:55389/authenticationservice-1.0-SNAPSHOT/api/auth/checktoken", params);
        if(response.getStatusLine().getStatusCode() == 200){
            return;
        }
        abortWithUnauthorized(requestContext);
    }
}
