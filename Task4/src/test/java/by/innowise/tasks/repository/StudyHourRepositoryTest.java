package by.innowise.tasks.repository;

import by.innowise.tasks.entity.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

@Sql(scripts = "/sql/insert.sql", executionPhase = BEFORE_TEST_METHOD)
class StudyHourRepositoryTest extends AbstractIntegrationDataBaseTest {

    public static final Timestamp DEFAULT_CLASS_DATE = new Timestamp(1L);
    public static final String DEFAULT_TYPE_LESSON = "lesson";
    public static final String DEFAULT_TYPE_EVENT = "event";
    public static final Room DEFAULT_ROOM = Room.builder()
            .id(2L)
            .type("type")
            .capacity(100)
            .build();
    public static final Teacher DEFAULT_TEACHER = Teacher.builder()
            .id(2L)
            .fio("fio")
            .degree("degree")
            .experience(5)
            .build();;
    public static final Subject DEFAULT_SUBJECT = Subject.builder()
            .id(2L)
            .name("subjectName")
            .build();;
    public static final String DEFAULT_TOPIC = "topic";
    public static final List<Speaker> DEFAULT_SPEAKER = new ArrayList<>();
    public static final Timestamp NEW_CLASS_DATE = new Timestamp(2L);

    public static final Lesson DEFAULT_LESSON = Lesson.builder()
            .classDate(DEFAULT_CLASS_DATE)
            .type(DEFAULT_TYPE_LESSON)
            .room(DEFAULT_ROOM)
            .teacher(DEFAULT_TEACHER)
            .subject(DEFAULT_SUBJECT)
            .build();

    public static final Event DEFAULT_EVENT = Event.builder()
            .classDate(DEFAULT_CLASS_DATE)
            .type(DEFAULT_TYPE_EVENT)
            .room(DEFAULT_ROOM)
            .topic(DEFAULT_TOPIC)
            .speakers(DEFAULT_SPEAKER)
            .build();

    @Autowired
    private StudyHourRepository studyHourRepository;

    @Test
    public void saveLesson() {
        assertEquals(DEFAULT_LESSON, studyHourRepository.save(DEFAULT_LESSON));
    }

    @Test
    public void saveEvent() {
        assertEquals(DEFAULT_EVENT, studyHourRepository.save(DEFAULT_EVENT));
    }

    @Test
    public void findStudyHourById() {
        assertTrue(studyHourRepository.findById(3L).isPresent());
    }

    @Test
    public void findAllStudyHours() {
        assertFalse(studyHourRepository.findAll().isEmpty());
    }

    @Test
    public void deleteStudyHour() {
        studyHourRepository.deleteById(3L);
        assertTrue(studyHourRepository.findAll().stream().allMatch(n -> n.getId() != 3));
    }

    @Test
    public void updateLesson() {
        System.out.println(studyHourRepository.findAll());
        DEFAULT_LESSON.setId(3L);
        studyHourRepository.findById(3L)
                .map(studyHour -> {
                            studyHour.setClassDate(NEW_CLASS_DATE);
                            return studyHourRepository.save(studyHour);
                        }
                ).orElseThrow();
        System.out.println(studyHourRepository.findAll());
        assertTrue(studyHourRepository.findById(3L).stream()
                .allMatch(studyHour -> studyHour.getClassDate().equals(NEW_CLASS_DATE)));
        System.out.println(studyHourRepository.findAll());
    }

    @Test
    public void updateEvent() {
        System.out.println(studyHourRepository.findAll());
        DEFAULT_EVENT.setId(4L);
        studyHourRepository.findById(4L)
                .map(studyHour -> {
                            studyHour.setClassDate(NEW_CLASS_DATE);
                            return studyHourRepository.save(studyHour);
                        }
                ).orElseThrow();
        System.out.println(studyHourRepository.findAll());
        assertTrue(studyHourRepository.findById(4L).stream()
                .allMatch(studyHour -> studyHour.getClassDate().equals(NEW_CLASS_DATE)));
        System.out.println(studyHourRepository.findAll());
    }
}