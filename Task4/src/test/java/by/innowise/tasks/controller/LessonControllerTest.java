package by.innowise.tasks.controller;

import by.innowise.tasks.Application;
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
import java.util.stream.Stream;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.only;

@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableAutoConfiguration
@AutoConfigureWebTestClient
@ActiveProfiles("test")
class LessonControllerTest {

    public static final String DEFAULT_PATH = "/json/lessonDto.json";
    public static final String DEFAULT_URI = "/lesson";
    public static final String DEFAULT_URI_WITH_ID = "/lesson/1";
    public static final Long DEFAULT_ID = 1L;
    public static final Timestamp DEFAULT_CLASS_DATE = null;
    public static final String DEFAULT_TYPE = "lesson";

    public static final Lesson DEFAULT_LESSON = Lesson.builder()
            .id(DEFAULT_ID)
            .classDate(DEFAULT_CLASS_DATE)
            .type(DEFAULT_TYPE)
            .build();

    @MockBean
    private StudyHourRepository studyHourRepository;

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void postLesson() {
        given(studyHourRepository.saveAndFlush(DEFAULT_LESSON)).willReturn(DEFAULT_LESSON);

        webTestClient.post()
                .uri(DEFAULT_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(Objects.requireNonNull(JsonReader.readJson(DEFAULT_PATH)))
                .exchange()
                .expectStatus()
                .isOk();

        then(studyHourRepository).should(only()).saveAndFlush(DEFAULT_LESSON);
    }

    @Test
    void putLesson() {
        given(studyHourRepository.save(DEFAULT_LESSON)).willReturn(DEFAULT_LESSON);

        webTestClient.put()
                .uri(DEFAULT_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(Objects.requireNonNull(JsonReader.readJson(DEFAULT_PATH)))
                .exchange()
                .expectStatus()
                .isOk();

        then(studyHourRepository).should(only()).save(DEFAULT_LESSON);
    }

    @Test
    void getAllLessons() {
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
    void getLessonById() {
        given(studyHourRepository.findById(DEFAULT_ID)).willReturn(Optional.ofNullable(DEFAULT_LESSON));

        webTestClient.get()
                .uri(DEFAULT_URI_WITH_ID)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk();

        then(studyHourRepository).should(only()).findById(DEFAULT_ID);
    }

    @Test
    void getLessonByIdNotFoundException() {
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
    void deleteLesson() {
        webTestClient.delete()
                .uri(DEFAULT_URI_WITH_ID)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk();

        then(studyHourRepository).should(only()).deleteById(DEFAULT_ID);
    }

}