package lt.tomas.vehicle_app_backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lt.tomas.vehicle_app_backend.security.JwtTokenProvider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthController.class)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testLogin_successful() throws Exception {
        // Duomenys, kuriuos siunčia klientas
        Map<String, String> loginRequest = Map.of(
                "username", "testuser",
                "password", "password123"
        );

        // Mockinam (sukuriam netikrus atsakymus)
        Authentication authMock = new UsernamePasswordAuthenticationToken("testuser", "password123");
        when(authenticationManager.authenticate(any())).thenReturn(authMock);
        when(jwtTokenProvider.generateToken("testuser")).thenReturn("fake-jwt-token");

        // Atliekam POST užklausą
        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("fake-jwt-token"))
                .andExpect(jsonPath("$.username").value("testuser"));
    }

    @Test
    void testLogin_invalidCredentials() throws Exception {
        Map<String, String> loginRequest = Map.of(
                "username", "wronguser",
                "password", "wrongpass"
        );

        // Simuliuojam neteisingą prisijungimą
        when(authenticationManager.authenticate(any())).thenThrow(new RuntimeException("Bad credentials"));

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string("Neteisingas vartotojo vardas arba slaptažodis."));
    }
}