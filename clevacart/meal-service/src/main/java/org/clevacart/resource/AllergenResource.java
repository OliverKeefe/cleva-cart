package org.clevacart.resource;

import jakarta.inject.Inject;
import jakarta.json.JsonObject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.clevacart.service.AllergenService;

@Path("/allergens")
public class AllergenResource {

    @Inject
    AllergenService allergenService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllergens() {
        JsonObject allergens = allergenService.getAll();
        return Response.ok(allergens).build();
    }

    @Path("/get-by-id/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllergenById(@PathParam("id") int id) {
        JsonObject allergen = allergenService.getById(id);
        return Response.ok(allergen).build();
    }


    @Path("/get-by-name/{name}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllergenByName(@PathParam("name") String name) {
        JsonObject allergen = allergenService.getByName(name);
        return Response.ok(allergen).build();
    }

}
