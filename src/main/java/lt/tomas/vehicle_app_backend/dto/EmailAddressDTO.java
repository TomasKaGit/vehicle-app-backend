package lt.tomas.vehicle_app_backend.dto;

public class EmailAddressDTO {
    private Long id;
    private String email;
    private Long departmentId;

    public EmailAddressDTO() {
    }

    public EmailAddressDTO(Long id, String email) {
        this.id = id;
        this.email = email;
    }

    public EmailAddressDTO(Long id, String email, Long departmentId) {
        this.id = id;
        this.email = email;
        this.departmentId = departmentId;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }
}
