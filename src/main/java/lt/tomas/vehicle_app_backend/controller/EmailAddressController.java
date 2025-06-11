package lt.tomas.vehicle_app_backend.controller;

import lt.tomas.vehicle_app_backend.dto.EmailAddressDTO;
import lt.tomas.vehicle_app_backend.entity.Department;
import lt.tomas.vehicle_app_backend.entity.EmailAddress;
import lt.tomas.vehicle_app_backend.repository.DepartmentRepository;
import lt.tomas.vehicle_app_backend.repository.EmailAddressRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/emails")
public class EmailAddressController {

    private final EmailAddressRepository emailAddressRepository;
    private final DepartmentRepository departmentRepository;

    public EmailAddressController(
            EmailAddressRepository emailAddressRepository,
            DepartmentRepository departmentRepository
    ) {
        this.emailAddressRepository = emailAddressRepository;
        this.departmentRepository = departmentRepository;
    }


    // URL: GET /api/emails/department/{id}
    // Grąžina: sąrašą EmailAddressDTO objektų

    @GetMapping("/department/{id}")
    public List<EmailAddressDTO> getEmailByDepartment(@PathVariable Long id) {
        return emailAddressRepository.findByDepartmentId(id).stream()
                .map(email -> new EmailAddressDTO(email.getId(), email.getEmail()))
                .toList();
    }


    // ➕ Pridėti el. paštą konkrečiam padaliniui
    // URL: POST /api/emails
    // Gaunamas JSON su `email` ir `departmentId`, saugomas į duomenų bazę.

    @PostMapping
    public ResponseEntity<EmailAddressDTO> addEmail(@RequestBody EmailAddressDTO dto) {
        Optional<Department> optionalDept = departmentRepository.findById(dto.getDepartmentId());
        if (optionalDept.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        // Sukuriamas naujas el. pašto įrašas
        EmailAddress newEmail = new EmailAddress(dto.getEmail(), optionalDept.get());
        EmailAddress saved = emailAddressRepository.save(newEmail);

        // Grąžinamas naujai sukurtas el. paštas DTO formatu
        return ResponseEntity.ok(new EmailAddressDTO(saved.getId(), saved.getEmail()));
    }


    // URL: DELETE /api/emails/{id}

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmail(@PathVariable Long id) {
        if (!emailAddressRepository.existsById(id)) {
            return ResponseEntity.notFound().build(); // jei nerandamas ID
        }

        emailAddressRepository.deleteById(id);
        return ResponseEntity.ok("El. pašto adresas ištrintas.");
    }
}
