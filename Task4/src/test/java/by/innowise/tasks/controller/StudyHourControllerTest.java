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
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

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
            .id(DEFAULT_ID)
            .classDate(DEFAULT_CLASS_DATE)
            .type(DEFAULT_TYPE_EVENT)
            .topic(DEFAULT_TOPIC)
            .build();

    public static final Lesson DEFAULT_LESSON = Lesson.builder()
            .id(DEFAULT_ID)
            .classDate(DEFAULT_CLASS_DATE)
            .type(DEFAULT_TYPE_LESSON)
            .build();

    @MockBean
    private StudyHourRepository studyHourRepository;

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void postStudyHourEvent() {
        given(studyHourRepository.saveAndFlush(DEFAULT_EVENT)).willReturn(DEFAULT_EVENT);

        webTestClient.post()
                .uri(DEFAULT_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(Objects.requireNonNull(JsonReader.readJson(DEFAULT_PATH_EVENT)))
                .exchange()
                .expectStatus()
                .isOk();

        then(studyHourRepository).should(only()).saveAndFlush(DEFAULT_EVENT);
    }

    @Test
    void postStudyHourLesson() {
        given(studyHourRepository.saveAndFlush(DEFAULT_LESSON)).willReturn(DEFAULT_LESSON);

        webTestClient.post()
                .uri(DEFAULT_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(Objects.requireNonNull(JsonReader.readJson(DEFAULT_PATH_LESSON)))
                .exchange()
                .expectStatus()
                .isOk();

        then(studyHourRepository).should(only()).saveAndFlush(DEFAULT_LESSON);
    }

    @Test
    void putStudyHourEvent() {
        given(studyHourRepository.save(DEFAULT_EVENT)).willReturn(DEFAULT_EVENT);
        given(studyHourRepository.findById(DEFAULT_ID)).willReturn(Optional.ofNullable(DEFAULT_EVENT));

        webTestClient.put()
                .uri(DEFAULT_URI)
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
        given(studyHourRepository.save(DEFAULT_LESSON)).willReturn(DEFAULT_LESSON);
        given(studyHourRepository.findById(DEFAULT_ID)).willReturn(Optional.ofNullable(DEFAULT_LESSON));

        webTestClient.put()
                .uri(DEFAULT_URI)
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
        given(studyHourRepository.save(DEFAULT_EVENT)).willReturn(DEFAULT_EVENT);
        given(studyHourRepository.findById(DEFAULT_ID)).willReturn(Optional.ofNullable(DEFAULT_LESSON));

        webTestClient.put()
                .uri(DEFAULT_URI)
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
        given(studyHourRepository.save(DEFAULT_LESSON)).willReturn(DEFAULT_LESSON);
        given(studyHourRepository.findById(DEFAULT_ID)).willReturn(Optional.ofNullable(DEFAULT_EVENT));

        webTestClient.put()
                .uri(DEFAULT_URI)
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
        given(studyHourRepository.getAll()).willReturn(Stream.ofNullable(DEFAULT_LESSON));

        webTestClient.get()
                .uri(DEFAULT_URI)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk();

        then(studyHourRepository).should(only()).getAll();
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