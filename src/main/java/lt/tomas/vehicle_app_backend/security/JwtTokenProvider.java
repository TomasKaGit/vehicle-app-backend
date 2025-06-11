package lt.tomas.vehicle_app_backend.security;

import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

import java.util.Date;


@Component
public class JwtTokenProvider {


    private final String secretKey = "veh1cleP@rk_S3cr3t_Key_1234567890!";

    private final long validityInMs = 1000 * 60 * 60 * 24;


    public String generateToken(String username) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + validityInMs);

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(expiry)
                .signWith(SignatureAlgorithm.HS256, secretKey.getBytes())
                .compact();
    }


    public String getUsernameFromToken(String token) {
        return getAllClaimsFromToken(token).getSubject();
    }


    public boolean validateToken(String token, org.springframework.security.core.userdetails.UserDetails userDetails) {
        String username = getUsernameFromToken(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }


    public boolean isTokenExpired(String token) {
        return getExpirationDateFromToken(token).before(new Date());
    }


    public Date getExpirationDateFromToken(String token) {
        return getAllClaimsFromToken(token).getExpiration();
    }


    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey.getBytes()) // ✅ Čia irgi būtina naudoti .getBytes()
                .parseClaimsJws(token)
                .getBody();
    }
}
