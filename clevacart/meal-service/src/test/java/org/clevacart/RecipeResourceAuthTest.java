package org.clevacart;

import io.quarkus.test.junit.QuarkusTest;
import io.smallrye.jwt.build.Jwt;
import org.apache.http.HttpHeaders;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.useRelaxedHTTPSValidation;
import static io.smallrye.jwt.build.Jwt.upn;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class RecipeResourceAuthTest {

    private String getJwt() {
        return Jwt.issuer("http://127.0.0.1:8081/token")
                .upn("test-user")
                .groups("User")
                .sign();
        }

    @Test
    public void testRecipeEndpoint() {
        String token = getJwt();

        Integer recipeId = 1;
        String recipeName = "Pizza";
        List<Integer> allergenIds = Arrays.asList(1, 2, 3);

        given()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .queryParam("id", recipeId)
                .queryParam("name", recipeName)
                .queryParam("allergenIds", allergenIds)
                .when()
                .get("/api/recipes/")
                .then()
                .statusCode(200)
                .body("someJsonPath", is("expectedValue"));

    }
}
