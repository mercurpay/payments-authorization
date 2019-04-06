package tech.claudioed.authorization.domain;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class authorizationTest {

    @Test
    public void testHelloEndpoint() {
        given()
          .when().get("/authorization")
          .then()
             .statusCode(200)
             .body(is("hello"));
    }

}