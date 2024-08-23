package org.clevacart.resource;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.clevacart.config.DatabaseConfig;
import org.clevacart.domain.model.Allergen;
import org.clevacart.domain.repository.AllergenRepository;
import org.clevacart.service.DatabaseService;

import java.util.List;

@Path("/allergens")
public class AllergenResource {

    @Inject
    DatabaseService databaseService;

    @Inject
    DatabaseConfig databaseConfig;

    @Inject
    AllergenRepository allergenRepository;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getAllergens() {
        List<String> allergens = allergenRepository.selectAllFromAllergens();
        return Response.ok(allergens).build();
    }

    AllergenRepository databaseTest = new AllergenRepository();

    public AllergenRepository getDatabaseTest() {
        return databaseTest;
    }

    @Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getAllergensById(@PathParam("id") int id) {
        Allergen allergen = allergenRepository.getAllergensById(id);

        if (allergen != null) {
            return Response.ok(allergen).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\":\"Allergen not found\"}")
                    .build();
        }
    }
}
