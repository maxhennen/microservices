package rest;

import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.primefaces.json.JSONObject;


import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

class KweetAPITest {

    private static String token;
    private String baseUri = "http://localhost:55389/kweetservice-1.0-SNAPSHOT/api/kwetter/";

    @BeforeAll
    public static void setup(){
        getToken();
    }

    @Test
    void findAll() {
        given()
                .header(new Header("Authorization", token))
                .when()
                .get(baseUri)
                .then()
                .log().all();
    }

    @Test
    void save() {
        given()
                .header(new Header("Authorization", token))
                .formParam("content", "Integratie test")
                .formParam("email", "test1@test.nl")
                .when()
                .post(baseUri)
                .then()
                .log().all();

    }

    @Test
    void findById() {
        given()
            .header(new Header("Authorization", token))
            .pathParam("id", 1)
            .when()
            .get(baseUri + "id/{id}")
            .then().log().all();
    }

    @Test
    void delete() {
        given()
                .header(new Header("Authorization", token))
                .pathParam("id", 2)
                .when()
                .delete(baseUri + "{id}")
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