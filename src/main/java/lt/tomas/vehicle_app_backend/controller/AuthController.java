package lt.tomas.vehicle_app_backend.controller;

import lt.tomas.vehicle_app_backend.security.JwtTokenProvider;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> request) {
        String username = request.get("username");
        String password = request.get("password");

        try {
            // Atliekame autentifikaciją – čia viskas per Spring Security
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );

            // Jei autentifikacija sėkminga – sukuriam JWT tokeną
            String token = jwtTokenProvider.generateToken(username);

            return ResponseEntity.ok(Map.of(
                    "token", token,
                    "username", username
            ));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(401).body("Neteisingas vartotojo vardas arba slaptažodis.");
        }
    }
}
