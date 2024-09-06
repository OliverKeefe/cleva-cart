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
    public Response getNutrients() {
        JsonObject nutrients = nutrientService.getAllNutrients();
        return Response.ok(nutrients).build();
    }

    @Path("/get-by-id/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getNutrientById(@PathParam("id") int id) {
    JsonObject nutrient = nutrientService.getNutrientById(id);
        return Response.ok(nutrient).build();
    }


    @Path("/get-by-name/{name}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getNutrientByName(@PathParam("name") String name) {
        JsonObject nutrient = nutrientService.getNutrientByName(name);
        return Response.ok(nutrient).build();
    }

}
