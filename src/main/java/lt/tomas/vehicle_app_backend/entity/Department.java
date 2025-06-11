package lt.tomas.vehicle_app_backend.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Department {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;


    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL)
    private List<Vehicle> vehicles = new ArrayList<>();


    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL)
    private List<EmailAddress> emails;


    public Department() {}


    public Department(String name, List<Vehicle> vehicles, List<EmailAddress> emails) {
        this.name = name;
        this.vehicles = vehicles;
        this.emails = emails;
    }


    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public List<Vehicle> getVehicles() { return vehicles; }
    public void setVehicles(List<Vehicle> vehicles) { this.vehicles = vehicles; }

    public List<EmailAddress> getEmails() { return emails; }
    public void setEmailAddresses(List<EmailAddress> emailAddresses) { this.emails = emailAddresses; }
    
}
