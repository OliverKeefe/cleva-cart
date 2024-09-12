package org.clevacart.resource;

import jakarta.inject.Inject;
import jakarta.json.JsonObject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.clevacart.dto.RecipeFilterDTO;
import org.clevacart.service.RecipeService;
import org.clevacart.dto.RecipeDTO;

import java.util.List;

@Path("/recipes")
public class RecipeResource {
    @Inject
    RecipeService recipeService;

    @Inject
    RecipeDTO recipeDTO;

    @Inject
    RecipeFilterDTO recipeFilterDTO;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRecipe(@QueryParam("id") Integer id,
                              @QueryParam("name") String name,
                              @QueryParam("allergenIds") List<Integer> allergenIds) {

        recipeFilterDTO.setId(id);
        recipeFilterDTO.setName(name);
        recipeFilterDTO.setAllergenIds(allergenIds);

        // Pass the filter to the service
        JsonObject recipes = recipeService.getByFilters(recipeFilterDTO);
        return Response.ok(recipes).build();
    }

    @Path("/all")
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

    @POST
    @Path("/add")
    @Consumes("application/json")
    public Response addRecipe(RecipeDTO recipeDTO) {
        JsonObject recipe = recipeService.addRecipe(recipeDTO);
        return Response.ok(recipe).build();
    }
}
