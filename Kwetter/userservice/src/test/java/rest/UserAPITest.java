package rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import domain.Details;
import domain.User;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.primefaces.json.JSONObject;

import java.util.ArrayList;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class UserAPITest {

    private static String token;
    private String baseUri = "http://localhost:55389/userservice-1.0-SNAPSHOT/api/user/";

    @BeforeAll
    public static void setUp() {
        getToken();
    }

    @Test
    void findAll() {
        given()
                .header(new Header("Authorization", token))
                .when()
                .get(baseUri)
                .then().log().all();
    }

    @Test
    void delete() {
        given()
                .header(new Header("Authorization", token))
                .pathParam("email", "test2@test.nl")
                .when()
                .delete(baseUri + "/{email}")
                .then().log().all();

    }

    @Test
    void findByEmail() {
        given()
                .header(new Header("Authorization", token))
                .pathParam("email", "test1@test.nl")
                .when()
                .get(baseUri + "findbyemail/{email}")
                .then().log().all();
    }

    @Test
    void following() {
        given()
                .header(new Header("Authorization", token))
                .pathParam("email", "test1@test.nl")
                .when()
                .get(baseUri + "following/{email}")
                .then().log().all();
    }

    @Test
    void followers() {
        given()
                .header(new Header("Authorization", token))
                .pathParam("email", "test1@test.nl")
                .when()
                .get(baseUri + "followers/{email}")
                .then().log().all();
    }

    @Test
    void unfollow() {
        given()
                .header(new Header("Authorization", token))
                .formParam("follower", "test1@test.nl")
                .formParam("following", "test2@test.nl")
                .when()
                .delete(baseUri)
                .then().log().all();
    }

    @Test
    void addFollower() {
        given()
                .header(new Header("Authorization", token))
                .formParam("follower", "test1@test.nl")
                .formParam("following", "test2@test.nl")
                .when()
                .post(baseUri)
                .then().log().all();
    }

    @Test
    void changeDetails() {
        given()
                .header(new Header("Authorization", token))
                .formParam("bio", "HALLO")
                .formParam("website", "www.google.com")
                .formParam("email", "test1@test.nl")
                .when()
                .post(baseUri + "changedetails")
                .then().log().all();
    }

    @Test
    void changeLocation() {
        given()
                .header(new Header("Authorization", token))
                .formParam("country", "NL")
                .formParam("city", "ehv")
                .formParam("street", "Rachelsmolen")
                .formParam("housenumber", "1")
                .formParam("email", "test1@test.nl")
                .when()
                .post(baseUri + "changelocation")
                .then().log().all();
    }

    @Test
    void editUser() {
        User user = new User(0, "max", "max@amx.nl", new ArrayList<>(), new Details());
        try {
            given()
                    .header(new Header("Authorization", token))
                    .body(new ObjectMapper().writer().withDefaultPrettyPrinter().writeValueAsString(user))
                    .when()
                    .get(baseUri + "changelocation")
                    .then().log().all();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    private static void getToken(){
        Response resp = given()
                .formParam("email", "test1@test.nl")
                .formParam("password", "Test123")
                .when()
                .post("http://localhost:55389/authenticationservice-1.0-SNAPSHOT/api/auth")
                .then()
                .contentType(ContentType.JSON)
                .extract()
                .response();

        JSONObject jsonObject = new JSONObject(resp.body().asString());
        token = "Bearer " + jsonObject.getString("token");
        resp.then().log().all();
        assertThat(resp.getStatusCode(), is(200));
    }
}