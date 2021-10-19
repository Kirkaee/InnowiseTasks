package by.innowise.tasks.repository;

import by.innowise.tasks.entity.Lesson;
import by.innowise.tasks.entity.Subject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

@Sql(scripts = "/sql/insert.sql", executionPhase = BEFORE_TEST_METHOD)
class SubjectRepositoryTest extends AbstractIntegrationDataBaseTest {

    public static final String DEFAULT_NAME = "subjectName";
    public static final List<Lesson> DEFAULT_LESSON = new ArrayList<>();
    public static final String NEW_NAME = "newName";

    public static final Subject DEFAULT_SUBJECT = Subject.builder()
            .name("subjectName")
            .lessons(new ArrayList<>())
            .build();

    @Autowired
    private SubjectRepository subjectRepository;

    @Test
    public void shouldSaveSubject() {
        assertEquals(DEFAULT_SUBJECT, subjectRepository.save(DEFAULT_SUBJECT));
    }

    @Test
    public void findById() {
        assertTrue(subjectRepository.findById(2L).isPresent());
    }

    @Test
    public void deleteSubject() {
        subjectRepository.deleteById(2L);
        assertTrue(subjectRepository.findAll().stream().allMatch(n -> n.getId() != 2));
    }

    @Test
    public void updateSubject() {
        subjectRepository.findById(2L)
                .map(subject -> {
                            subject.setName(NEW_NAME);
                            return subjectRepository.save(subject);
                        }
                ).orElseThrow();
        assertTrue(subjectRepository.findById(2L).stream()
                .allMatch(subject -> subject.getName().equals(NEW_NAME)));
    }

}