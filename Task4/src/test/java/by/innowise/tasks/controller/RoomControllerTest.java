package by.innowise.tasks.controller;

import by.innowise.tasks.Application;
import by.innowise.tasks.entity.Event;
import by.innowise.tasks.entity.Room;
import by.innowise.tasks.entity.StudyHour;
import by.innowise.tasks.repository.RoomRepository;
import by.innowise.tasks.util.JsonReader;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.only;

@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableAutoConfiguration
@AutoConfigureWebTestClient
@ActiveProfiles("test")
class RoomControllerTest {

    public static final String DEFAULT_PATH = "/json/roomDto.json";
    public static final String DEFAULT_URI = "/room";
    public static final String DEFAULT_URI_WITH_ID = "/room/1";
    public static final Long DEFAULT_ID = 1L;
    public static final String DEFAULT_TYPE = "typeRoom";
    public static final Integer DEFAULT_CAPACITY = 100;
    public static final Event DEFAULT_STUDY_HOUR = Event.builder()
            .id(1L)
            .type("event")
            .build();
    public static final List<StudyHour> DEFAULT_STUDY_HOURS = List.of(DEFAULT_STUDY_HOUR);

    public static final Room DEFAULT_ROOM = Room.builder()
            .id(DEFAULT_ID)
            .type(DEFAULT_TYPE)
            .capacity(DEFAULT_CAPACITY)
            .studyHours(DEFAULT_STUDY_HOURS)
            .build();


    @MockBean
    private RoomRepository roomRepository;

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void postRoom() {
        given(roomRepository.saveAndFlush(DEFAULT_ROOM)).willReturn(DEFAULT_ROOM);

        webTestClient.post()
                .uri(DEFAULT_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(Objects.requireNonNull(JsonReader.readJson(DEFAULT_PATH)))
                .exchange()
                .expectStatus()
                .isOk();

        then(roomRepository).should(only()).saveAndFlush(DEFAULT_ROOM);
    }

    @Test
    void putRoom() {
        given(roomRepository.save(DEFAULT_ROOM)).willReturn(DEFAULT_ROOM);

        webTestClient.put()
                .uri(DEFAULT_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(Objects.requireNonNull(JsonReader.readJson(DEFAULT_PATH)))
                .exchange()
                .expectStatus()
                .isOk();

        then(roomRepository).should(only()).save(DEFAULT_ROOM);
    }

    @Test
    void getAllRooms() {
        given(roomRepository.getAll()).willReturn(Stream.ofNullable(DEFAULT_ROOM));

        webTestClient.get()
                .uri(DEFAULT_URI)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk();

        then(roomRepository).should(only()).getAll();
    }

    @Test
    void getRoomById() {
        given(roomRepository.findById(DEFAULT_ID)).willReturn(Optional.ofNullable(DEFAULT_ROOM));

        webTestClient.get()
                .uri(DEFAULT_URI_WITH_ID)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk();

        then(roomRepository).should(only()).findById(DEFAULT_ID);
    }

    @Test
    void getRoomByIdNotFoundException() {
        given(roomRepository.findById(DEFAULT_ID)).willReturn(Optional.empty());

        webTestClient.get()
                .uri(DEFAULT_URI_WITH_ID)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isNotFound();

        then(roomRepository).should(only()).findById(DEFAULT_ID);
    }

    @Test
    void deleteRoom() {
        webTestClient.delete()
                .uri(DEFAULT_URI_WITH_ID)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk();

        then(roomRepository).should(only()).deleteById(DEFAULT_ID);
    }
}