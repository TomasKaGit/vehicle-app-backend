package lt.tomas.vehicle_app_backend.controller;

import lt.tomas.vehicle_app_backend.dto.VehicleDTO;
import lt.tomas.vehicle_app_backend.entity.Department;
import lt.tomas.vehicle_app_backend.entity.Vehicle;
import lt.tomas.vehicle_app_backend.repository.DepartmentRepository;
import lt.tomas.vehicle_app_backend.repository.VehicleRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/vehicles")
public class VehicleController {

    private final VehicleRepository vehicleRepository;
    private final DepartmentRepository departmentRepository;

    public VehicleController(VehicleRepository vehicleRepository, DepartmentRepository departmentRepository) {
        this.vehicleRepository = vehicleRepository;
        this.departmentRepository = departmentRepository;
    }

    @GetMapping
    public List<Vehicle> getAll() {
        return vehicleRepository.findAll();
    }

    @GetMapping("/department/{id}")
    public List<VehicleDTO> getByDepartment(@PathVariable Long id) {
        return vehicleRepository.findByDepartmentId(id).stream()
                .map(v -> new VehicleDTO(
                        v.getId(),
                        v.getBrand().toUpperCase(),
                        v.getModel().toUpperCase(),
                        v.getYear(),
                        v.getRegistrationNumber(),
                        v.getInsuranceExpiry().toString(),
                        v.getTechnicalInspectionExpiry().toString(),
                        v.isBroken(),
                        v.getDepartment() != null ? v.getDepartment().getName() : null
                ))
                .toList();
    }

    @GetMapping("/search")
    public ResponseEntity<VehicleDTO> searchByReg(@RequestParam String registration) {
        return vehicleRepository.findByRegistrationNumberIgnoreCase(registration)
                .map(v -> ResponseEntity.ok(new VehicleDTO(
                        v.getId(),
                        v.getBrand().toUpperCase(),
                        v.getModel().toUpperCase(),
                        v.getYear(),
                        v.getRegistrationNumber(),
                        v.getInsuranceExpiry().toString(),
                        v.getTechnicalInspectionExpiry().toString(),
                        v.isBroken(),
                        v.getDepartment() != null ? v.getDepartment().getName() : null
                )))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<VehicleDTO> addVehicle(@RequestBody Vehicle vehicle) {
        if (vehicle.getRegistrationNumber() != null) {
            vehicle.setRegistrationNumber(vehicle.getRegistrationNumber().toUpperCase());
        }

        if (vehicle.getDepartment() != null) {
            Optional<Department> dept = departmentRepository.findById(vehicle.getDepartment().getId());
            dept.ifPresent(vehicle::setDepartment);
        }

        Vehicle saved = vehicleRepository.save(vehicle);

        VehicleDTO dto = new VehicleDTO(
                saved.getId(),
                saved.getBrand().toUpperCase(),
                saved.getModel().toUpperCase(),
                saved.getYear(),
                saved.getRegistrationNumber(),
                saved.getInsuranceExpiry().toString(),
                saved.getTechnicalInspectionExpiry().toString(),
                saved.isBroken(),
                saved.getDepartment() != null ? saved.getDepartment().getName() : null
        );

        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        vehicleRepository.deleteById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<VehicleDTO> updateVehicle(@PathVariable Long id, @RequestBody Vehicle updatedVehicle) {
        return vehicleRepository.findById(id).map(existing -> {
            existing.setBrand(updatedVehicle.getBrand().toUpperCase());
            existing.setModel(updatedVehicle.getModel().toUpperCase());
            existing.setYear(updatedVehicle.getYear());
            existing.setRegistrationNumber(updatedVehicle.getRegistrationNumber());
            existing.setInsuranceExpiry(updatedVehicle.getInsuranceExpiry());
            existing.setTechnicalInspectionExpiry(updatedVehicle.getTechnicalInspectionExpiry());
            existing.setBroken(updatedVehicle.isBroken());

            // Jei reikia – atnaujinti department
            if (updatedVehicle.getDepartment() != null && updatedVehicle.getDepartment().getId() != null) {
                departmentRepository.findById(updatedVehicle.getDepartment().getId())
                        .ifPresent(existing::setDepartment);
            }

            Vehicle saved = vehicleRepository.save(existing);

            // Sukuriam DTO atsakymui
            VehicleDTO dto = new VehicleDTO(
                    saved.getId(),
                    saved.getBrand().toUpperCase(),
                    saved.getModel().toUpperCase(),
                    saved.getYear(),
                    saved.getRegistrationNumber(),
                    saved.getInsuranceExpiry().toString(),
                    saved.getTechnicalInspectionExpiry().toString(),
                    saved.isBroken(),
                    saved.getDepartment() != null ? saved.getDepartment().getName() : null
            );

            return ResponseEntity.ok(dto);
        }).orElse(ResponseEntity.notFound().build());
    }
}
