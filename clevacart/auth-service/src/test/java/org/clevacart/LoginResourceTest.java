package org.clevacart;

import io.quarkus.test.junit.QuarkusTest;
import org.clevacart.dto.LoginRequestDTO;
import org.clevacart.entity.UserEntity;
import org.clevacart.service.LoginService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import jakarta.ws.rs.core.Response;

import static io.restassured.RestAssured.when;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
public class LoginResourceTest {

    @Mock
    LoginService loginService;

    @Mock
    UserEntity userEntity;

    @InjectMocks
    LoginResource loginResource;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testLoginSuccess() {
        LoginRequestDTO loginRequestDTO = new LoginRequestDTO();
        loginRequestDTO.username = "testUser";
        loginRequestDTO.password = "correctPassword";

        when(loginService.authenticate("testUser", "correctPassword")).thenReturn(true);
        when(userEntity.getUsername()).thenReturn("testUser");
        when(loginService.generateToken("testUser")).thenReturn("mocked-jwt-token");

        Response response = loginResource.login(loginRequestDTO);

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals("Bearer mocked-jwt-token", response.getHeaderString("Authorization"));
        assertEquals("mocked-jwt-token", response.getEntity());
    }

    @Test
    public void testLoginFailure() {
        LoginRequestDTO loginRequestDTO = new LoginRequestDTO();
        loginRequestDTO.username = "testUser";
        loginRequestDTO.password = "wrongPassword";

        when(loginService.authenticate("testUser", "wrongPassword")).thenReturn(false);

        Response response = loginResource.login(loginRequestDTO);

        assertEquals(Response.Status.UNAUTHORIZED.getStatusCode(), response.getStatus());
        assertEquals("Invalid credentials", response.getEntity());
    }
}
