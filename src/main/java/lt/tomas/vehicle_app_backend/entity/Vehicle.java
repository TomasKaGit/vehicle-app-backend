package lt.tomas.vehicle_app_backend.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String brand;
    private String model;
    private int year;
    private String registrationNumber;
    private LocalDate insuranceExpiry;
    private LocalDate technicalInspectionExpiry;

    @Column(name = "is_broken")// ar automobilis sugedes?
    @JsonProperty("broken")
    private Boolean broken = false;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;


    public Vehicle() {}


    public Vehicle(String brand, String model, int year, String registrationNumber,
                   LocalDate insuranceExpiry, LocalDate technicalInspectionExpiry,
                   Boolean broken, Department department) {
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.registrationNumber = registrationNumber;
        this.insuranceExpiry = insuranceExpiry;
        this.technicalInspectionExpiry = technicalInspectionExpiry;
        this.broken = broken;
        this.department = department;
    }


    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }

    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }

    public int getYear() { return year; }
    public void setYear(int year) { this.year = year; }

    public String getRegistrationNumber() { return registrationNumber; }
    public void setRegistrationNumber(String registrationNumber) { this.registrationNumber = registrationNumber; }

    public LocalDate getInsuranceExpiry() { return insuranceExpiry; }
    public void setInsuranceExpiry(LocalDate insuranceExpiry) { this.insuranceExpiry = insuranceExpiry; }

    public LocalDate getTechnicalInspectionExpiry() { return technicalInspectionExpiry; }
    public void setTechnicalInspectionExpiry(LocalDate technicalInspectionExpiry) { this.technicalInspectionExpiry = technicalInspectionExpiry; }

    public Boolean isBroken() { return broken; }
    public void setBroken(Boolean broken) { this.broken = broken; }

    public Department getDepartment() { return department; }
    public void setDepartment(Department department) { this.department = department; }
}
