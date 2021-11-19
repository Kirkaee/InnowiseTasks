package by.innowise.tasks.controller;

import by.innowise.tasks.Application;
import by.innowise.tasks.entity.Lesson;
import by.innowise.tasks.entity.Teacher;
import by.innowise.tasks.repository.TeacherRepository;
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
class TeacherControllerTest {

    public static final String DEFAULT_PATH = "/json/teacherDto.json";
    public static final String DEFAULT_URI = "/teacher";
    public static final String DEFAULT_URI_WITH_ID = "/teacher/1";
    public static final Long DEFAULT_ID = 1L;
    public static final String DEFAULT_FIO = "fio";
    public static final String DEFAULT_DEGREE = "degree";
    public static final Integer DEFAULT_EXPERIENCE = 5;
    public static final Lesson DEFAULT_LESSON = Lesson.builder()
            .id(1L)
            .build();
    public static final List<Lesson> DEFAULT_LESSONS = List.of(DEFAULT_LESSON);

    public static final Teacher DEFAULT_TEACHER = Teacher.builder()
            .id(DEFAULT_ID)
            .fio(DEFAULT_FIO)
            .degree(DEFAULT_DEGREE)
            .experience(DEFAULT_EXPERIENCE)
            .lessons(DEFAULT_LESSONS)
            .build();

    @MockBean
    private TeacherRepository teacherRepository;

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void postDepartment() {
        given(teacherRepository.saveAndFlush(DEFAULT_TEACHER)).willReturn(DEFAULT_TEACHER);

        webTestClient.post()
                .uri(DEFAULT_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(Objects.requireNonNull(JsonReader.readJson(DEFAULT_PATH)))
                .exchange()
                .expectStatus()
                .isOk();

        then(teacherRepository).should(only()).saveAndFlush(DEFAULT_TEACHER);
    }

    @Test
    void putDepartment() {
        given(teacherRepository.save(DEFAULT_TEACHER)).willReturn(DEFAULT_TEACHER);

        webTestClient.put()
                .uri(DEFAULT_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(Objects.requireNonNull(JsonReader.readJson(DEFAULT_PATH)))
                .exchange()
                .expectStatus()
                .isOk();

        then(teacherRepository).should(only()).save(DEFAULT_TEACHER);
    }

    @Test
    void getAllTeachers() {
        given(teacherRepository.getAll()).willReturn(Stream.ofNullable(DEFAULT_TEACHER));

        webTestClient.get()
                .uri(DEFAULT_URI)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk();

        then(teacherRepository).should(only()).getAll();
    }

    @Test
    void getTeacherById() {
        given(teacherRepository.findById(DEFAULT_ID)).willReturn(Optional.ofNullable(DEFAULT_TEACHER));

        webTestClient.get()
                .uri(DEFAULT_URI_WITH_ID)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk();

        then(teacherRepository).should(only()).findById(DEFAULT_ID);
    }

    @Test
    void getTeacherByIdNotFoundException() {
        given(teacherRepository.findById(DEFAULT_ID)).willReturn(Optional.empty());

        webTestClient.get()
                .uri(DEFAULT_URI_WITH_ID)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isNotFound();

        then(teacherRepository).should(only()).findById(DEFAULT_ID);
    }

    @Test
    void deleteTeacher() {
        webTestClient.delete()
                .uri(DEFAULT_URI_WITH_ID)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk();

        then(teacherRepository).should(only()).deleteById(DEFAULT_ID);
    }

}