package by.innowise.tasks.controller;

import by.innowise.tasks.Application;
import by.innowise.tasks.entity.Lesson;
import by.innowise.tasks.entity.Subject;
import by.innowise.tasks.repository.SubjectRepository;
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

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.only;

@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableAutoConfiguration
@AutoConfigureWebTestClient
@ActiveProfiles("test")
class SubjectControllerTest {

    public static final String DEFAULT_PATH = "/json/subjectDto.json";
    public static final String DEFAULT_URI = "/subject";
    public static final String DEFAULT_URI_WITH_ID = "/subject/1";
    public static final Long DEFAULT_ID = 1L;
    public static final String DEFAULT_NAME = "subjectName";
    public static final Lesson DEFAULT_LESSON = Lesson.builder()
            .id(1L)
            .build();
    public static final List<Lesson> DEFAULT_LESSONS = List.of(DEFAULT_LESSON);

    public static final Subject DEFAULT_SUBJECT = Subject.builder()
            .name(DEFAULT_NAME)
            .lessons(DEFAULT_LESSONS)
            .build();

    @MockBean
    private SubjectRepository subjectRepository;

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void postSubject() {
        DEFAULT_SUBJECT.setId(null);
        given(subjectRepository.save(DEFAULT_SUBJECT)).willReturn(DEFAULT_SUBJECT);

        webTestClient.post()
                .uri(DEFAULT_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(Objects.requireNonNull(JsonReader.readJson(DEFAULT_PATH)))
                .exchange()
                .expectStatus()
                .isOk();

        then(subjectRepository).should(only()).save(DEFAULT_SUBJECT);
    }

    @Test
    void putSubject() {
        DEFAULT_SUBJECT.setId(DEFAULT_ID);
        given(subjectRepository.save(DEFAULT_SUBJECT)).willReturn(DEFAULT_SUBJECT);

        webTestClient.put()
                .uri(DEFAULT_URI_WITH_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(Objects.requireNonNull(JsonReader.readJson(DEFAULT_PATH)))
                .exchange()
                .expectStatus()
                .isOk();

        then(subjectRepository).should(only()).save(DEFAULT_SUBJECT);
    }

    @Test
    void getAllSubjects() {
        given(subjectRepository.findAll()).willReturn(List.of(DEFAULT_SUBJECT));

        webTestClient.get()
                .uri(DEFAULT_URI)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk();

        then(subjectRepository).should(only()).findAll();
    }

    @Test
    void getSubjectById() {
        given(subjectRepository.findById(DEFAULT_ID)).willReturn(Optional.ofNullable(DEFAULT_SUBJECT));

        webTestClient.get()
                .uri(DEFAULT_URI_WITH_ID)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk();

        then(subjectRepository).should(only()).findById(DEFAULT_ID);
    }

    @Test
    void getSubjectByIdNotFoundException() {
        given(subjectRepository.findById(DEFAULT_ID)).willReturn(Optional.empty());

        webTestClient.get()
                .uri(DEFAULT_URI_WITH_ID)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isNotFound();

        then(subjectRepository).should(only()).findById(DEFAULT_ID);
    }

    @Test
    void deleteSubject() {
        webTestClient.delete()
                .uri(DEFAULT_URI_WITH_ID)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk();

        then(subjectRepository).should(only()).deleteById(DEFAULT_ID);
    }
}
