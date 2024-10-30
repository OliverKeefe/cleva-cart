package org.clevacart.resource;

import io.quarkus.security.User;
import io.quarkus.security.identity.SecurityIdentity;
import io.smallrye.jwt.build.Jwt;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jdk.jshell.spi.ExecutionControl;

import java.time.Duration;
import java.util.HashSet;
import java.util.Set;

@Path("/token")
public class TokenResource {

    @POST
    @Produces(MediaType.TEXT_PLAIN)
    public Response generateToken() {
        // Hardcoded values for testing
        String username = "testUser";
        Set<String> roles = new HashSet<>();
        roles.add("User");

        String token = Jwt
                .issuer("https://clevacart.com")
                .subject(username)
                .groups(roles)
                .expiresIn(Duration.ofHours(2))
                .sign();

        return Response.ok(token).build();
    }
}