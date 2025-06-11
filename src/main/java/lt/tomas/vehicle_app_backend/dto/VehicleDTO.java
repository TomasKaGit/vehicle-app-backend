package lt.tomas.vehicle_app_backend.dto;

public class VehicleDTO {
    private Long id;
    private String brand;
    private String model;
    private int year;
    private String registrationNumber;
    private String insuranceExpiry;
    private String technicalInspectionExpiry;
    private Boolean broken;
    private String departmentName;

    public VehicleDTO(Long id, String brand, String model, int year,
                      String registrationNumber, String insuranceExpiry,
                      String technicalInspectionExpiry, Boolean broken,
                      String departmentName) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.registrationNumber = registrationNumber;
        this.insuranceExpiry = insuranceExpiry;
        this.technicalInspectionExpiry = technicalInspectionExpiry;
        this.broken = broken;
        this.departmentName = departmentName;
    }

    public Long getId() { return id; }
    public String getBrand() { return brand; }
    public String getModel() { return model; }
    public int getYear() { return year; }
    public String getRegistrationNumber() { return registrationNumber; }
    public String getInsuranceExpiry() { return insuranceExpiry; }
    public String getTechnicalInspectionExpiry() { return technicalInspectionExpiry; }
    public Boolean isBroken() { return broken; }
    public String getDepartmentName() { return departmentName; }
}
