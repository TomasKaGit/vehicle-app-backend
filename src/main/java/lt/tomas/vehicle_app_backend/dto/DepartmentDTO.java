package lt.tomas.vehicle_app_backend.dto;

import java.util.List;

public class DepartmentDTO {
    private Long id;
    private String name;
    private List<VehicleDTO> vehicles;
    private List<EmailAddressDTO> emails;

    public DepartmentDTO(Long id, String name, List<VehicleDTO> vehicles, List<EmailAddressDTO> emails) {
        this.id = id;
        this.name = name;
        this.vehicles = vehicles;
        this.emails = emails;
    }

    public Long getId() {return id;}
    public String getName() {return name;}
    public List<VehicleDTO> getVehicles() {return vehicles;}
    public List<EmailAddressDTO> getEmails() {return emails;}
}
