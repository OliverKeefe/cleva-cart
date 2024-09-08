package org.clevacart.resource;

import jakarta.inject.Inject;
import jakarta.json.JsonObject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.clevacart.service.RecipeService;
import org.clevacart.dto.RecipeDTO;

@Path("/recipes")
public class RecipeResource {
    @Inject
    RecipeService recipeService;

    @Inject
    RecipeDTO recipeDTO;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRecipes() {
        JsonObject recipes = recipeService.getAll();
        return Response.ok(recipes).build();
    }

    @Path("/get-by-id/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRecipeById(@PathParam("id") int id) {
        JsonObject recipes = recipeService.getById(id);
        return Response.ok(recipes).build();
    }

    @Path("/get-by-name/{name}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRecipeByName(@PathParam("name") String name) {
        JsonObject recipe = recipeService.getByName(name);
        return Response.ok(recipe).build();
    }

    // TODO: Add functionality to handle List<IngredientEntity>.
    @POST
    @Path("/add")
    @Consumes("application/json")
    public Response addRecipe(RecipeDTO recipeDTO) {
        JsonObject recipe = recipeService.addRecipe(recipeDTO);
        return Response.ok(recipe).build();
    }
}
