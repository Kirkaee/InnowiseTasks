package by.innowise.tasks.repository;

import by.innowise.tasks.entity.Department;
import by.innowise.tasks.entity.Faculty;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

@Sql(scripts = "/sql/insert.sql", executionPhase = BEFORE_TEST_METHOD)
class FacultyRepositoryTest extends AbstractIntegrationDataBaseTest {

    public static final String DEFAULT_NAME = "facultyName";
    public static final List<Department> DEFAULT_DEPARTMENT = new ArrayList<>();
    public static final String NEW_NAME = "newName";

    public static final Faculty DEFAULT_FACULTY = Faculty.builder()
            .name(DEFAULT_NAME)
            .departments(DEFAULT_DEPARTMENT)
            .build();

    @Autowired
    private FacultyRepository facultyRepository;

    @Test
    public void saveFaculty() {
        assertEquals(DEFAULT_FACULTY, facultyRepository.save(DEFAULT_FACULTY));
    }

    @Test
    public void findByIdFaculty() {
        assertTrue(facultyRepository.findById(2L).isPresent());
    }

    @Test
    public void findAllFaculties() {
        assertFalse(facultyRepository.findAll().isEmpty());
    }

    @Test
    public void deleteFaculty() {
        facultyRepository.deleteById(2L);
        assertTrue(facultyRepository.findAll().stream().allMatch(n -> n.getId() != 2));
    }

    @Test
    public void updateFaculty() {
        facultyRepository.findById(2L)
                .map(faculty -> {
                            faculty.setName(NEW_NAME);
                            return facultyRepository.save(faculty);
                        }
                ).orElseThrow();
        assertTrue(facultyRepository.findById(2L).stream()
                .allMatch(faculty -> faculty.getName().equals(NEW_NAME)));
    }

}