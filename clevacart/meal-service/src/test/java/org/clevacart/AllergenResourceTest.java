package org.clevacart;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.equalTo;

@QuarkusTest
public class AllergenResourceTest {

    @Test
    public void testAllergenResource() {
        given()
                .when().get("/get-by-name/gluten")
                .then()
                .statusCode(200)
                .body("name", equalTo("Gluten"));
    }
}