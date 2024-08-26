package org.clevacart.resource;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.clevacart.config.DatabaseConfig;
import org.clevacart.domain.model.Allergen;
import org.clevacart.domain.repository.AllergenRepository;
import org.clevacart.service.DatabaseService;

import java.sql.SQLException;
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

    @Path("get-by-id/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getAllergensById(@PathParam("id") int id) {
        System.out.println("Received ID: " + id);
        Allergen allergen;
        try {
            allergen = allergenRepository.getAllergensById(id);
            System.out.println("Allergen retrieved: " + allergen);
        } catch (Exception e) {
            System.err.println("Exception occurred while retrieving allergen: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\":\"Internal server error\"}")
                    .build();
        }

        if (allergen != null) {
            return Response.ok(allergen.getName()).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\":\"Allergen not found\"}")
                    .build();
        }
    }

    @Path("/test")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response test() throws SQLException {
        Allergen allergen = allergenRepository.test();

        if (allergen != null) {
            return Response.ok(allergen.getName()).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\":\"Allergen not found\"}")
                    .build();
        }
    }

    @Path("get-by-name/{name}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getAllergensByName(@PathParam("name") String name) throws SQLException {
        Allergen allergen = allergenRepository.getAllergensByName(name);

        if (allergen != null) {
            return Response.ok(allergen.getName()).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\":\"Allergen not found\"}")
                    .build();
        }
    }
}
