package org.clevacart.service;

import io.quarkus.security.User;
import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.ApplicationPath;
import org.clevacart.entity.UserEntity;

import java.util.HashMap;
import java.util.Map;

@ApplicationScoped
public class LoginService {

    @Inject
    UserEntity userEntity;

    public boolean authenticate(String username, String password) {
        return username.equals(userEntity.getUsername()) && password.equals(userEntity.getPassword());
    }

    public String generateToken(String username) {
        // Might need this, might not - TODO: decide.
        // Map<String, Object> claims = new HashMap<>();
        // claims.put("role", "User");

        return Jwt.issuer("https://clevacart.com")
                .upn(username)
                .groups("User")
                .claim("role", "User")
                .sign();
    }
}
