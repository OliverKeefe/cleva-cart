package org.clevacart.resource;

import jakarta.inject.Inject;
import jakarta.json.JsonObject;
import jakarta.ws.rs.*;
        import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.clevacart.service.IngredientService;

@Path("/ingredients")
public class IngredientResource {

    @Inject
    IngredientService ingredientService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getIngredients() {
        JsonObject ingredients = ingredientService.getAll();
        return Response.ok(ingredients).build();
    }

    @Path("/get-by-id/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getIngredientById(@PathParam("id") int id) {
        JsonObject ingredient = ingredientService.getById(id);
        return Response.ok(ingredient).build();
    }


    @Path("/get-by-name/{name}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getIngredientByName(@PathParam("name") String name) {
        JsonObject ingredient = ingredientService.getByName(name);
        return Response.ok(ingredient).build();
    }

}
