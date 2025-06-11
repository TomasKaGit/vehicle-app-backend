package lt.tomas.vehicle_app_backend.entity;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
public class User implements UserDetails {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //  Vartotojo vardas – privalomas ir turi būti unikalus
    @Column(unique = true, nullable = false)
    private String username;

    //  Užšifruotas slaptažodis – taip pat privalomas
    @Column(nullable = false)
    private String password;

    //  Vartotojo rolė (pvz., ROLE_USER arba ROLE_ADMIN) – naudojama autorizacijai
    @Column(nullable = false)
    private String role = "ROLE_USER";


    public User() {}


    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }


    public Long getId() {
        return id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    //  UserDetails metodai (būtini Spring Security veikimui)


    //  Grąžina vartotojo teises (roles)
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Lambda sukuria GrantedAuthority objektą su rolės reikšme
        return List.of(() -> role);
    }

    //  Grąžina užšifruotą slaptažodį
    @Override
    public String getPassword() {
        return password;
    }

    //  Grąžina prisijungimo vardą
    @Override
    public String getUsername() {
        return username;
    }

    //  metodai nusako vartotojo paskyros būklę

    @Override
    public boolean isAccountNonExpired() {
        return true; // paskyra negalioja? – ne, galioja
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // paskyra užrakinta? – ne, atrakinta
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // slaptažodis pasenęs? – ne, galioja
    }

    @Override
    public boolean isEnabled() {
        return true; // paskyra aktyvi? – taip
    }
}
