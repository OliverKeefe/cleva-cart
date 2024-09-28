package org.clevacart.resource;

import io.quarkus.security.User;
import io.smallrye.jwt.build.Jwt;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import jdk.jshell.spi.ExecutionControl;

import java.time.Duration;
import java.util.HashSet;
import java.util.Set;

@Path("/auth")
public class AuthResource {

    @POST
    @Path("/login")
    public  Response login(User user) {
        return Response.ok().build();
    }
}
