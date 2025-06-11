package lt.tomas.vehicle_app_backend.repository;

import lt.tomas.vehicle_app_backend.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;



public interface DepartmentRepository extends JpaRepository<Department, Long> {
}