package org.clevacart.resource;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.clevacart.config.DatabaseConfig;
import org.clevacart.domain.repository.DatabaseTest;
import org.clevacart.service.DatabaseService;

@Path("/allergens")
public class AllergenResource {

    @Inject
    DatabaseService databaseService;

    @Inject
    DatabaseConfig databaseConfig;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        DatabaseConfig.DatabaseConnectionProperties properties = databaseConfig.getDatabaseConnectionProperties();
        return databaseTest.selectAllFromAllergens(databaseService, properties);
    }

    DatabaseTest databaseTest = new DatabaseTest();

    public DatabaseTest getDatabaseTest() {
        return databaseTest;
    }
}
