package lt.tomas.vehicle_app_backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lt.tomas.vehicle_app_backend.dto.DepartmentDTO;
import lt.tomas.vehicle_app_backend.entity.Department;
import lt.tomas.vehicle_app_backend.repository.DepartmentRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DepartmentController.class)
class DepartmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DepartmentRepository departmentRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("GET /api/departments grąžina 200 OK")
    void getAllDepartments_returnsOk() throws Exception {
        Department d = new Department();
        d.setId(1L);
        d.setName("Transportas");

        Mockito.when(departmentRepository.findAll()).thenReturn(List.of(d));

        mockMvc.perform(get("/api/departments"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("Transportas"));
    }

    @Test
    @DisplayName("GET /api/departments/1 kai departamentas egzistuoja")
    void getDepartmentById_returnsOk() throws Exception {
        Department d = new Department();
        d.setId(1L);
        d.setName("IT skyrius");

        Mockito.when(departmentRepository.findById(1L)).thenReturn(Optional.of(d));

        mockMvc.perform(get("/api/departments/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("IT skyrius"));
    }

    @Test
    @DisplayName("GET /api/departments/99 kai departamentas neegzistuoja")
    void getDepartmentById_returnsNotFound() throws Exception {
        Mockito.when(departmentRepository.findById(99L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/departments/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("POST /api/departments sukuria naują departamentą")
    void createNewDepartment_returnsCreated() throws Exception {
        Department d = new Department();
        d.setId(5L);
        d.setName("Naujas");

        Mockito.when(departmentRepository.save(any())).thenReturn(d);

        mockMvc.perform(post("/api/departments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Naujas\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(5L))
                .andExpect(jsonPath("$.name").value("Naujas"));
    }

    @Test
    @DisplayName("DELETE /api/departments/1 kai departamentas egzistuoja")
    void deleteDepartment_returnsOk() throws Exception {
        Mockito.when(departmentRepository.existsById(1L)).thenReturn(true);

        mockMvc.perform(delete("/api/departments/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Departamentas ištrintas."));
    }

    @Test
    @DisplayName("DELETE /api/departments/99 kai departamentas neegzistuoja")
    void deleteDepartment_returnsNotFound() throws Exception {
        Mockito.when(departmentRepository.existsById(99L)).thenReturn(false);

        mockMvc.perform(delete("/api/departments/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("PUT /api/departments/1 atnaujina pavadinimą")
    void updateDepartment_returnsOk() throws Exception {
        Department existing = new Department();
        existing.setId(1L);
        existing.setName("Senasis");

        Mockito.when(departmentRepository.findById(1L)).thenReturn(Optional.of(existing));
        Mockito.when(departmentRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        mockMvc.perform(put("/api/departments/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Naujas\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Naujas"));
    }

    @Test
    @DisplayName("PUT /api/departments/99 kai departamentas neegzistuoja")
    void updateDepartment_returnsNotFound() throws Exception {
        Mockito.when(departmentRepository.findById(99L)).thenReturn(Optional.empty());

        mockMvc.perform(put("/api/departments/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Betkoks\"}"))
                .andExpect(status().isNotFound());
    }
}