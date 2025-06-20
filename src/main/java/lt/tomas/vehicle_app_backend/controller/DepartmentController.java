package lt.tomas.vehicle_app_backend.controller;

import lt.tomas.vehicle_app_backend.dto.DepartmentDTO;
import lt.tomas.vehicle_app_backend.dto.EmailAddressDTO;
import lt.tomas.vehicle_app_backend.dto.VehicleDTO;
import lt.tomas.vehicle_app_backend.entity.Department;
import lt.tomas.vehicle_app_backend.repository.DepartmentRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/departments")
public class DepartmentController {

    private final DepartmentRepository departmentRepository;

    public DepartmentController(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }


    // URL: GET /api/departments

    @GetMapping
    public List<DepartmentDTO> getAllDepartments() {
        List<Department> departments = departmentRepository.findAll();

        return departments.stream()
                .map(dept -> new DepartmentDTO(
                        dept.getId(),
                        dept.getName(),
                        //  Konvertuojame transporto priemones į VehicleDTO
                        dept.getVehicles().stream()
                                .map(vehicle -> new VehicleDTO(
                                        vehicle.getId(),
                                        vehicle.getBrand(),
                                        vehicle.getModel(),
                                        vehicle.getYear(),
                                        vehicle.getRegistrationNumber(),
                                        vehicle.getInsuranceExpiry().toString(),
                                        vehicle.getTechnicalInspectionExpiry().toString(),
                                        vehicle.isBroken(),
                                        vehicle.getDepartment() != null ? vehicle.getDepartment().getName() : null
                                ))
                                .toList(),
                        // Konvertuojame el. paštus į EmailAddressDTO
                        dept.getEmails().stream()
                                .map(email -> new EmailAddressDTO(
                                        email.getId(),
                                        email.getEmail()
                                ))
                                .toList()
                ))
                .toList();
    }



    // URL: GET /api/departments/{id}

    @GetMapping("/{id}")
    public ResponseEntity<DepartmentDTO> getDepartmentById(@PathVariable Long id) {
        return departmentRepository.findById(id)
                .map(dept -> ResponseEntity.ok(new DepartmentDTO(
                        dept.getId(),
                        dept.getName(),
                        dept.getVehicles().stream()
                                .map(vehicle -> new VehicleDTO(
                                        vehicle.getId(),
                                        vehicle.getBrand(),
                                        vehicle.getModel(),
                                        vehicle.getYear(),
                                        vehicle.getRegistrationNumber(),
                                        vehicle.getInsuranceExpiry().toString(),
                                        vehicle.getTechnicalInspectionExpiry().toString(),
                                        vehicle.isBroken(),
                                        dept.getName()
                                ))
                                .toList(),
                        dept.getEmails().stream()
                                .map(email -> new EmailAddressDTO(
                                        email.getId(),
                                        email.getEmail()
                                ))
                                .toList()
                )))
                .orElse(ResponseEntity.notFound().build()); // jei nerandam, grąžinam 404
    }


    // URL: POST /api/departments

    @PostMapping
    public DepartmentDTO createNewDepartment(@RequestBody Department department) {
        Department saved = departmentRepository.save(department);

        return new DepartmentDTO(
                saved.getId(),
                saved.getName(),
                List.of(),
                List.of()
        );
    }


    // URL: DELETE /api/departments/{id}

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDepartment(@PathVariable Long id) {
        if (!departmentRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        departmentRepository.deleteById(id);
        return ResponseEntity.ok("Departamentas ištrintas.");
    }


    // URL: PUT /api/departments/{id}

    @PutMapping("/{id}")
    public ResponseEntity<DepartmentDTO> updateDepartmentNameById(
            @PathVariable Long id,
            @RequestBody DepartmentDTO updatedDto) {

        return departmentRepository.findById(id)
                .map(existing -> {
                    existing.setName(updatedDto.getName());
                    departmentRepository.save(existing);

                    return ResponseEntity.ok(new DepartmentDTO(
                            existing.getId(),
                            existing.getName(),
                            List.of(),
                            List.of()
                    ));
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
