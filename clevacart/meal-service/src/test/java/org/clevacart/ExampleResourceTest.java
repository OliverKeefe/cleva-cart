package org.clevacart;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
class ExampleResourceTest {
    @Test
    void testAllergenResource() {
        given()
                .when().get("/allergens/1")
                .then()
                .statusCode(200)
                .body(is("Gluten"));
    }

}