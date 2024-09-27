package org.clevacart.resource;

import jakarta.inject.Inject;
import jakarta.json.JsonObject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.clevacart.dto.NutrientFilterDTO;
import org.clevacart.service.NutrientService;

import java.util.List;

@Path("/nutrients")
public class NutrientResource {

    @Inject
    NutrientService nutrientService;

    @Inject
    NutrientFilterDTO nutrientFilterDTO;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRecipe(@QueryParam("id") Integer id,
                              @QueryParam("name") String name,
                              @QueryParam("allergenIds") List<Integer> allergenIds) {

        nutrientFilterDTO.setId(id);
        nutrientFilterDTO.setName(name);
        nutrientFilterDTO.setAllergenIds(allergenIds);

        // Pass the filter to the service
        JsonObject recipes = nutrientService.getByFilters(nutrientFilterDTO);
        return Response.ok(recipes).build();
    }

    @Path("/all")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getNutrients() {
        JsonObject nutrients = nutrientService.getAll();
        return Response.ok(nutrients).build();
    }

    @Path("/get-by-id/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getNutrientById(@PathParam("id") int id) {
    JsonObject nutrient = nutrientService.getById(id);
        return Response.ok(nutrient).build();
    }


    @Path("/get-by-name/{name}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getNutrientByName(@PathParam("name") String name) {
        JsonObject nutrient = nutrientService.getByName(name);
        return Response.ok(nutrient).build();
    }

}
