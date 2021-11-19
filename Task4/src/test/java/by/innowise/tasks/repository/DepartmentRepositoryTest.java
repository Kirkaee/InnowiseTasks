package by.innowise.tasks.repository;

import by.innowise.tasks.entity.Department;
import by.innowise.tasks.entity.Faculty;
import by.innowise.tasks.entity.Teacher;
import net.minidev.json.JSONUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

@Sql(scripts = "/sql/insert.sql", executionPhase = BEFORE_TEST_METHOD)
class DepartmentRepositoryTest extends AbstractIntegrationDataBaseTest {

    public static final String DEFAULT_NAME = "nameDepartment";
    public static final Faculty DEFAULT_FACULTY = Faculty.builder()
            .id(2L)
            .build();
    public static final List<Teacher> DEFAULT_TEACHERS = new ArrayList<>();
    public static final String NEW_NAME = "newName";


    public static final Department DEFAULT_DEPARTMENT = Department.builder()
            .name(DEFAULT_NAME)
            .faculty(DEFAULT_FACULTY)
            .teachers(DEFAULT_TEACHERS)
            .build();

    @Autowired
    private DepartmentRepository departmentRepository;

    @Test
    public void saveDepartment() {
        assertEquals(DEFAULT_DEPARTMENT, departmentRepository.saveAndFlush(DEFAULT_DEPARTMENT));
    }

    @Test
    public void findByIdDepartment() {
        assertTrue(departmentRepository.findById(2L).isPresent());
    }

    @Test
    public void findAllDepartments() {
        assertTrue(departmentRepository.getAll().findFirst().isPresent());
    }

    @Test
    public void deleteDepartment() {
        departmentRepository.deleteById(2L);
        assertTrue(departmentRepository.findAll().stream().allMatch(n -> n.getId() != 2));
    }

    @Test
    public void updateDepartment() {
        departmentRepository.findById(2L)
                .map(department -> {
                            department.setName(NEW_NAME);
                            return departmentRepository.save(department);
                        }
                ).orElseThrow();
        assertTrue(departmentRepository.findById(2L).stream()
                .allMatch(department -> department.getName().equals(NEW_NAME)));
    }

}