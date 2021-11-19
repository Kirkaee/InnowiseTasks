package by.innowise.tasks.repository;

import by.innowise.tasks.entity.Room;
import by.innowise.tasks.entity.StudyHour;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;


@Sql(scripts = "/sql/insert.sql", executionPhase = BEFORE_TEST_METHOD)
class RoomRepositoryTest extends AbstractIntegrationDataBaseTest {

    public static final String DEFAULT_TYPE = "type";
    public static final Integer DEFAULT_CAPACITY = 100;
    public static final List<StudyHour> DEFAULT_STUDY_HOUR = new ArrayList<>();
    public static final Integer NEW_CAPACITY = 50;

    public static final Room DEFAULT_ROOM = Room.builder()
            .type(DEFAULT_TYPE)
            .capacity(DEFAULT_CAPACITY)
            .studyHours(DEFAULT_STUDY_HOUR)
            .build();

    @Autowired
    private RoomRepository roomRepository;

    @Test
    public void saveRoom() {
        assertEquals(DEFAULT_ROOM, roomRepository.saveAndFlush(DEFAULT_ROOM));
    }

    @Test
    public void findByIdRoom() {
        assertTrue(roomRepository.findById(2L).isPresent());
    }

    @Test
    public void findAllRoom() {
        assertTrue(roomRepository.getAll().findFirst().isPresent());
    }

    @Test
    public void deleteRoom() {
        roomRepository.deleteById(2L);
        assertTrue(roomRepository.findAll().stream().allMatch(n -> n.getId() != 2));
    }

    @Test
    public void updateRoom() {
        roomRepository.findById(2L)
                .map(room -> {
                            room.setCapacity(NEW_CAPACITY);
                            return roomRepository.save(room);
                        }
                ).orElseThrow();
        assertTrue(roomRepository.findById(2L).stream()
                .allMatch(room -> room.getCapacity().equals(NEW_CAPACITY)));
    }
}