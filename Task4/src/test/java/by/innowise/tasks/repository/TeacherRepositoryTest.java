package by.innowise.tasks.repository;

import by.innowise.tasks.entity.Teacher;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

@Sql(scripts = "/sql/insert.sql", executionPhase = BEFORE_TEST_METHOD)
class TeacherRepositoryTest extends AbstractIntegrationDataBaseTest {

    public static final String DEFAULT_FIO = "fio";
    public static final String DEFAULT_DEGREE = "degree";
    public static final String NEW_FIO = "newFio";

    public static final Teacher DEFAULT_TEACHER = Teacher.builder()
            .fio("fioTeacher")
            .degree("degreeTeacher")
            .build();

    @Autowired
    private TeacherRepository teacherRepository;

    @Test
    public void saveTeacher() {
        assertEquals(DEFAULT_TEACHER, teacherRepository.saveAndFlush(DEFAULT_TEACHER));
    }

    @Test
    public void findTeacherById() {
        assertTrue(teacherRepository.findById(2L).isPresent());
    }

    @Test
    public void findAllTeachers() {
        assertTrue(teacherRepository.getAll().findFirst().isPresent());
    }

    @Test
    public void deleteTeacher() {
        teacherRepository.deleteById(2L);
        assertTrue(teacherRepository.findAll().stream().allMatch(n -> n.getId() != 2));
    }

    @Test
    public void updateTeacher() {
        teacherRepository.findById(2L)
                .map(teacher -> {
                            teacher.setFio(NEW_FIO);
                            return teacherRepository.save(teacher);
                        }
                ).orElseThrow();
        assertTrue(teacherRepository.findById(2L).stream()
                .allMatch(teacher -> teacher.getFio().equals(NEW_FIO)));
    }
}