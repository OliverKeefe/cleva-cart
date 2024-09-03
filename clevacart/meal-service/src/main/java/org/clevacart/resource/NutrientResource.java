package org.clevacart.resource;

import jakarta.inject.Inject;
import jakarta.json.JsonObject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.clevacart.service.NutrientService;

@Path("/nutrients")
public class NutrientResource {

    @Inject
    NutrientService nutrientService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllergens() {
        JsonObject allergens = nutrientService.getAllNutrients();
        return Response.ok(allergens).build();
    }

    @Path("/get-by-id/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getNutrientById(@PathParam("id") int id) {
        JsonObject allergen = nutrientService.getNutrientById(id);
        return Response.ok(allergen).build();
    }


    @Path("/get-by-name/{name}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getNutrientByName(@PathParam("name") String name) {
        JsonObject allergen = nutrientService.getNutrientByName(name);
        return Response.ok(allergen).build();
    }

}
