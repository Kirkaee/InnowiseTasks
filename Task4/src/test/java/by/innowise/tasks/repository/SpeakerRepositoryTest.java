package by.innowise.tasks.repository;

import by.innowise.tasks.entity.Event;
import by.innowise.tasks.entity.Speaker;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

@Sql(scripts = "/sql/insert.sql", executionPhase = BEFORE_TEST_METHOD)
class SpeakerRepositoryTest extends AbstractIntegrationDataBaseTest {

    public static final String DEFAULT_FIO = "fio";
    public static final String DEFAULT_TITLE = "title";
    public static final String DEFAULT_MEMBERSHIP = "membership";
    public static final List<Event> DEFAULT_EVENT = new ArrayList<>();
    public static final String NEW_FIO = "newFio";

    public static final Speaker DEFAULT_SPEAKER = Speaker.builder()
            .fio(DEFAULT_FIO)
            .title(DEFAULT_TITLE)
            .membership(DEFAULT_MEMBERSHIP)
            .events(DEFAULT_EVENT)
            .build();

    @Autowired
    private SpeakerRepository speakerRepository;

    @Test
    public void shouldSaveSpeaker() {
        assertEquals(DEFAULT_SPEAKER, speakerRepository.save(DEFAULT_SPEAKER));
    }

    @Test
    public void findById() {
        assertTrue(speakerRepository.findById(2L).isPresent());
    }


    @Test
    public void deleteSpeaker() {
        speakerRepository.deleteById(2L);
        assertTrue(speakerRepository.findAll().stream().allMatch(n -> n.getId() != 2));
    }

    @Test
    public void updateSpeaker() {
        speakerRepository.findById(2L)
                .map(speaker -> {
                            speaker.setFio(NEW_FIO);
                            return speakerRepository.save(speaker);
                        }
                ).orElseThrow();
        assertTrue(speakerRepository.findById(2L).stream()
                .allMatch(speaker -> speaker.getFio().equals(NEW_FIO)));
    }

}