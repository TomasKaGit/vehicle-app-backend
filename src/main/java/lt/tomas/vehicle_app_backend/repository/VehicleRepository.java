package lt.tomas.vehicle_app_backend.repository;

import lt.tomas.vehicle_app_backend.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    List<Vehicle> findByDepartmentId(Long departmentId);
    Optional<Vehicle> findByRegistrationNumberIgnoreCase(String registrationNumber);
}
