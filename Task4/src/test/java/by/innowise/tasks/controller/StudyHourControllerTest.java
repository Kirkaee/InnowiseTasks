package by.innowise.tasks.controller;

import by.innowise.tasks.Application;
import by.innowise.tasks.entity.Event;
import by.innowise.tasks.entity.Lesson;
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
import static org.mockito.Mockito.times;

@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableAutoConfiguration
@AutoConfigureWebTestClient
@ActiveProfiles("test")
class StudyHourControllerTest {

    public static final String DEFAULT_PATH_EVENT = "/json/eventDto.json";
    public static final String DEFAULT_PATH_LESSON = "/json/lessonDto.json";
    public static final String DEFAULT_URI = "/studyHour";
    public static final String DEFAULT_URI_WITH_ID = "/studyHour/1";
    public static final Long DEFAULT_ID = 1L;
    public static final Timestamp DEFAULT_CLASS_DATE = null;
    public static final String DEFAULT_TYPE_EVENT = "event";
    public static final String DEFAULT_TYPE_LESSON = "lesson";
    public static final String DEFAULT_TOPIC = "topic";

    public static final Event DEFAULT_EVENT = Event.builder()
            .classDate(DEFAULT_CLASS_DATE)
            .type(DEFAULT_TYPE_EVENT)
            .topic(DEFAULT_TOPIC)
            .build();

    public static final Lesson DEFAULT_LESSON = Lesson.builder()
            .classDate(DEFAULT_CLASS_DATE)
            .type(DEFAULT_TYPE_LESSON)
            .build();

    @MockBean
    private StudyHourRepository studyHourRepository;

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void postStudyHourEvent() {
        DEFAULT_EVENT.setId(null);
        given(studyHourRepository.save(DEFAULT_EVENT)).willReturn(DEFAULT_EVENT);

        webTestClient.post()
                .uri(DEFAULT_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(Objects.requireNonNull(JsonReader.readJson(DEFAULT_PATH_EVENT)))
                .exchange()
                .expectStatus()
                .isOk();

        then(studyHourRepository).should(only()).save(DEFAULT_EVENT);
    }

    @Test
    void postStudyHourLesson() {
        DEFAULT_LESSON.setId(null);
        given(studyHourRepository.save(DEFAULT_LESSON)).willReturn(DEFAULT_LESSON);

        webTestClient.post()
                .uri(DEFAULT_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(Objects.requireNonNull(JsonReader.readJson(DEFAULT_PATH_LESSON)))
                .exchange()
                .expectStatus()
                .isOk();

        then(studyHourRepository).should(only()).save(DEFAULT_LESSON);
    }

    @Test
    void putStudyHourEvent() {
        DEFAULT_EVENT.setId(DEFAULT_ID);
        given(studyHourRepository.save(DEFAULT_EVENT)).willReturn(DEFAULT_EVENT);
        given(studyHourRepository.findById(DEFAULT_ID)).willReturn(Optional.ofNullable(DEFAULT_EVENT));

        webTestClient.put()
                .uri(DEFAULT_URI_WITH_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(Objects.requireNonNull(JsonReader.readJson(DEFAULT_PATH_EVENT)))
                .exchange()
                .expectStatus()
                .isOk();

        then(studyHourRepository).should(times(1)).save(DEFAULT_EVENT);
        then(studyHourRepository).should(times(1)).findById(DEFAULT_ID);
        then(studyHourRepository).shouldHaveNoMoreInteractions();
    }

    @Test
    void putStudyHourLesson() {
        DEFAULT_LESSON.setId(DEFAULT_ID);
        given(studyHourRepository.save(DEFAULT_LESSON)).willReturn(DEFAULT_LESSON);
        given(studyHourRepository.findById(DEFAULT_ID)).willReturn(Optional.ofNullable(DEFAULT_LESSON));

        webTestClient.put()
                .uri(DEFAULT_URI_WITH_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(Objects.requireNonNull(JsonReader.readJson(DEFAULT_PATH_LESSON)))
                .exchange()
                .expectStatus()
                .isOk();

        then(studyHourRepository).should(times(1)).save(DEFAULT_LESSON);
        then(studyHourRepository).should(times(1)).findById(DEFAULT_ID);
        then(studyHourRepository).shouldHaveNoMoreInteractions();
    }

    @Test
    void putStudyHourLessonToEvent() {
        DEFAULT_EVENT.setId(DEFAULT_ID);
        given(studyHourRepository.save(DEFAULT_EVENT)).willReturn(DEFAULT_EVENT);
        given(studyHourRepository.findById(DEFAULT_ID)).willReturn(Optional.ofNullable(DEFAULT_LESSON));

        webTestClient.put()
                .uri(DEFAULT_URI_WITH_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(Objects.requireNonNull(JsonReader.readJson(DEFAULT_PATH_EVENT)))
                .exchange()
                .expectStatus()
                .isOk();

        then(studyHourRepository).should(times(1)).save(DEFAULT_EVENT);
        then(studyHourRepository).should(times(1)).deleteById(DEFAULT_ID);
        then(studyHourRepository).should(times(1)).findById(DEFAULT_ID);
        then(studyHourRepository).shouldHaveNoMoreInteractions();
    }

    @Test
    void putStudyHourEventToLesson() {
        DEFAULT_LESSON.setId(DEFAULT_ID);
        given(studyHourRepository.save(DEFAULT_LESSON)).willReturn(DEFAULT_LESSON);
        given(studyHourRepository.findById(DEFAULT_ID)).willReturn(Optional.ofNullable(DEFAULT_EVENT));

        webTestClient.put()
                .uri(DEFAULT_URI_WITH_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(Objects.requireNonNull(JsonReader.readJson(DEFAULT_PATH_LESSON)))
                .exchange()
                .expectStatus()
                .isOk();

        then(studyHourRepository).should(times(1)).save(DEFAULT_LESSON);
        then(studyHourRepository).should(times(1)).deleteById(DEFAULT_ID);
        then(studyHourRepository).should(times(1)).findById(DEFAULT_ID);
        then(studyHourRepository).shouldHaveNoMoreInteractions();
    }

    @Test
    void getAllStudyHours() {
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
    void getStudyHourById() {
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
    void getStudyHourByIdNotFound() {
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
    void deleteStudyHour() {
        webTestClient.delete()
                .uri(DEFAULT_URI_WITH_ID)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk();

        then(studyHourRepository).should(only()).deleteById(DEFAULT_ID);
    }

}