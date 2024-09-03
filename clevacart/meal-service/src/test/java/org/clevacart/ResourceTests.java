package org.clevacart;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.equalTo;

@QuarkusTest
public class ResourceTests {

    @Test
    public void testAllergenResource() {
        given()
                .when().get("/get-by-name/gluten")
                .then()
                .statusCode(200)
                .body("name", equalTo("Gluten"));
    }

    @Test
    public void testIngredientResource() {
        given()
                .when().get("/get-by-name/wheat")
                .then()
                .statusCode(200)
                .body("name", equalTo("Wheat"));
    }

    @Test
    public void testNutrientResource() {
        given()
                .when().get("/get-by-name/Vitamin C")
                .then()
                .statusCode(200)
                .body("name", equalTo("Vitamin C"));
    }
}