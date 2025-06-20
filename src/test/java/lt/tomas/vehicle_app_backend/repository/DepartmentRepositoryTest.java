package lt.tomas.vehicle_app_backend.repository;

import lt.tomas.vehicle_app_backend.entity.Department;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class DepartmentRepositoryTest {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Test
    void testSaveAndFindById() {
        // Sukuriame department objektą
        Department department = new Department();
        department.setName("IT skyrius");

        // Išsaugome jį DB
        Department saved = departmentRepository.save(department);

        // Surandame pagal ID
        Optional<Department> found = departmentRepository.findById(saved.getId());

        // Patikriname
        assertTrue(found.isPresent());
        assertEquals("IT skyrius", found.get().getName());
    }

    @Test
    void testDeleteDepartment() {
        Department department = new Department();
        department.setName("Finansai");

        Department saved = departmentRepository.save(department);
        Long id = saved.getId();

        departmentRepository.deleteById(id);

        Optional<Department> found = departmentRepository.findById(id);
        assertFalse(found.isPresent());
    }
}