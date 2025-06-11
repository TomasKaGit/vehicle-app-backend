package lt.tomas.vehicle_app_backend.entity;

import jakarta.persistence.*;

@Entity
public class EmailAddress {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;


    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;


    public EmailAddress() {}


    public EmailAddress(String email, Department department) {
        this.email = email;
        this.department = department;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }
}
