package by.innowise.tasks.controller;

import by.innowise.tasks.Application;
import by.innowise.tasks.entity.Event;
import by.innowise.tasks.repository.StudyHourRepository;
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

import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.only;

@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableAutoConfiguration
@AutoConfigureWebTestClient
@ActiveProfiles("test")
class EventControllerTest {

    public static final String DEFAULT_PATH = "/json/eventDto.json";
    public static final String DEFAULT_URI = "/event";
    public static final String DEFAULT_URI_WITH_ID = "/event/1";
    public static final Long DEFAULT_ID = 1L;
    public static final Timestamp DEFAULT_CLASS_DATE = null;
    public static final String DEFAULT_TYPE = "event";
    public static final String DEFAULT_TOPIC = "topic";

    public static final Event DEFAULT_EVENT = Event.builder()
            .classDate(DEFAULT_CLASS_DATE)
            .type(DEFAULT_TYPE)
            .topic(DEFAULT_TOPIC)
            .build();

    @MockBean
    private StudyHourRepository studyHourRepository;

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void postEvent() {
        DEFAULT_EVENT.setId(null);
        given(studyHourRepository.save(DEFAULT_EVENT)).willReturn(DEFAULT_EVENT);

        webTestClient.post()
                .uri(DEFAULT_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(Objects.requireNonNull(JsonReader.readJson(DEFAULT_PATH)))
                .exchange()
                .expectStatus()
                .isOk();

        then(studyHourRepository).should(only()).save(DEFAULT_EVENT);
    }

    @Test
    void putEvent() {
        DEFAULT_EVENT.setId(DEFAULT_ID);
        given(studyHourRepository.save(DEFAULT_EVENT)).willReturn(DEFAULT_EVENT);

        webTestClient.put()
                .uri(DEFAULT_URI_WITH_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(Objects.requireNonNull(JsonReader.readJson(DEFAULT_PATH)))
                .exchange()
                .expectStatus()
                .isOk();

        then(studyHourRepository).should(only()).save(DEFAULT_EVENT);
    }

    @Test
    void getAllEvents() {
        given(studyHourRepository.findAll()).willReturn(List.of(DEFAULT_EVENT));

        webTestClient.get()
                .uri(DEFAULT_URI)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk();

        then(studyHourRepository).should(only()).findAll();
    }

    @Test
    void getEventById() {
        given(studyHourRepository.findById(DEFAULT_ID)).willReturn(Optional.ofNullable(DEFAULT_EVENT));

        webTestClient.get()
                .uri(DEFAULT_URI_WITH_ID)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk();

        then(studyHourRepository).should(only()).findById(DEFAULT_ID);
    }

    @Test
    void getEventByIdNotFoundException() {
        given(studyHourRepository.findById(DEFAULT_ID)).willReturn(Optional.empty());

        webTestClient.get()
                .uri(DEFAULT_URI_WITH_ID)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isNotFound();

        then(studyHourRepository).should(only()).findById(DEFAULT_ID);
    }

    @Test
    void deleteEvent() {
        webTestClient.delete()
                .uri(DEFAULT_URI_WITH_ID)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk();

        then(studyHourRepository).should(only()).deleteById(DEFAULT_ID);
    }
}