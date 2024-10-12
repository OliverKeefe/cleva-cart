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

public class TokenResource {

    private final SecurityIdentity securityIdentity;

    public TokenResource(SecurityIdentity securityIdentity) {
        this.securityIdentity = securityIdentity;
    }

    @POST
    @Produces(MediaType.TEXT_PLAIN)
    public Response generateToken() {
        Set<String> roles = new HashSet<>(securityIdentity.getRoles());

        String token = Jwt
                .issuer("https://clevacart.com")
                .subject(securityIdentity.getPrincipal().getName())
                .groups(roles)
                .expiresIn(Duration.ofHours(2))
                .sign();

        return Response.ok(token).build();
    }

}
