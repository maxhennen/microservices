import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.primefaces.json.JSONObject;


import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

class AuthUserTest {

    private String baseUri = "http://localhost:55389/authenticationservice-1.0-SNAPSHOT/api/";
    private String token;

    @Test
    public void health(){
        get(baseUri + "health/check").then().log().all();
    }

    @Test
    public void getToken(){
        Response resp = given()
                .formParam("email", "test1@test.nl")
                .formParam("password", "Test123")
                .when()
                .post(baseUri + "auth")
                .then()
                .contentType(ContentType.JSON)
                .extract()
                .response();

        JSONObject jsonObject = new JSONObject(resp.body().asString());
        token = jsonObject.getString("token");
        resp.then().log().all();
        assertThat(resp.getStatusCode(), is(200));
    }

    @Test
    public void logout(){
        Response resp = given().formParam("email", "test1@test.nl")
                .when()
                .post(baseUri + "auth/logout")
                .then()
                .contentType(ContentType.JSON)
                .extract()
                .response();
        resp.then().log().all();
        assertThat(resp.getStatusCode(), is(200));
    }

    @Test
    public void checktoken(){
        getToken();
        Response resp = given().formParam("token", token)
                .when()
                .post(baseUri + "auth/checktoken")
                .then()
                .contentType(ContentType.JSON)
                .extract()
                .response();
        resp.then().log().all();
        assertThat(resp.getStatusCode(), is(200));
    }
}