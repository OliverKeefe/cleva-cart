package org.clevacart.resource;

import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import io.quarkus.security.User;
import org.clevacart.dto.LoginRequestDTO;
import org.clevacart.entity.UserEntity;
import org.clevacart.service.LoginService;

@Path("/login")
public class LoginResource {

    @Inject
    LoginService loginService;

    @Inject
    UserEntity userEntity;

    @Inject
    LoginRequestDTO loginRequestDTO;

    @POST
    public Response login(LoginRequestDTO loginRequestDTO) {
        String username = loginRequestDTO.username;
        String password = loginRequestDTO.password;

        if (loginService.authenticate(username, password)) {

            String token = loginService.generateToken(userEntity.getUsername());
            return Response.ok(token)
                    .header("Authorization", "Bearer " + token)
                    .build();

        } else {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity("Invalid credentials")
                    .build();
        }
    }
}
