package lt.tomas.vehicle_app_backend.repository;

import lt.tomas.vehicle_app_backend.entity.EmailAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface EmailAddressRepository extends JpaRepository<EmailAddress, Long> {
    List<EmailAddress> findByDepartmentId(Long departmentId);
}
