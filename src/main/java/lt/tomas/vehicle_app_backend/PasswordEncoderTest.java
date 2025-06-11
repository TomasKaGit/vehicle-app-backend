package lt.tomas.vehicle_app_backend;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


public class PasswordEncoderTest {
    public static void main(String[] args) {
        //  Sukuriame šifravimo įrankį (naudojamas ir Spring Security sistemoje)
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        //  Užšifruojame slaptažodį "0000"
        String hash = encoder.encode("0000");

        // 🖨 Spausdiname užšifruotą slaptažodį – galima kopijuoti į DB
        System.out.println("Užšifruotas slaptažodis: " + hash);
    }
}
