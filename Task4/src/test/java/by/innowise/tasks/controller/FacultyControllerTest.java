package by.innowise.tasks.controller;

import by.innowise.tasks.Application;
import by.innowise.tasks.entity.Department;
import by.innowise.tasks.entity.Faculty;
import by.innowise.tasks.repository.FacultyRepository;
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

import java.util.ArrayList;
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
class FacultyControllerTest {

    public static final String DEFAULT_PATH = "/json/facultyDto.json";
    public static final String DEFAULT_URI = "/faculty";
    public static final String DEFAULT_URI_WITH_ID = "/faculty/1";
    public static final Long DEFAULT_ID = 1L;
    public static final String DEFAULT_NAME = "nameFaculty";
    public static final Department DEFAULT_DEPARTMENT = Department.builder()
            .id(1L)
            .name("departmentName")
            .teachers(new ArrayList<>())
            .build();
    public static final List<Department> DEFAULT_DEPARTMENTS = List.of(DEFAULT_DEPARTMENT);

    public static final Faculty DEFAULT_FACULTY = Faculty.builder()
            .name(DEFAULT_NAME)
            .departments(DEFAULT_DEPARTMENTS)
            .build();

    @MockBean
    private FacultyRepository facultyRepository;

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void postFaculty() {
        DEFAULT_FACULTY.setId(null);
        given(facultyRepository.save(DEFAULT_FACULTY)).willReturn(DEFAULT_FACULTY);

        webTestClient.post()
                .uri(DEFAULT_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(Objects.requireNonNull(JsonReader.readJson(DEFAULT_PATH)))
                .exchange()
                .expectStatus()
                .isOk();

        then(facultyRepository).should(only()).save(DEFAULT_FACULTY);
    }

    @Test
    void putFaculty() {
        DEFAULT_FACULTY.setId(DEFAULT_ID);
        given(facultyRepository.save(DEFAULT_FACULTY)).willReturn(DEFAULT_FACULTY);

        webTestClient.put()
                .uri(DEFAULT_URI_WITH_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(Objects.requireNonNull(JsonReader.readJson(DEFAULT_PATH)))
                .exchange()
                .expectStatus()
                .isOk();

        then(facultyRepository).should(only()).save(DEFAULT_FACULTY);
    }

    @Test
    void getFaculty() {
        given(facultyRepository.findAll()).willReturn(List.of(DEFAULT_FACULTY));

        webTestClient.get()
                .uri(DEFAULT_URI)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk();

        then(facultyRepository).should(only()).findAll();
    }

    @Test
    void getFacultyById() {
        given(facultyRepository.findById(DEFAULT_ID)).willReturn(Optional.ofNullable(DEFAULT_FACULTY));

        webTestClient.get()
                .uri(DEFAULT_URI_WITH_ID)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk();

        then(facultyRepository).should(only()).findById(DEFAULT_ID);
    }

    @Test
    void getFacultyByIdNotFound() {
        given(facultyRepository.findById(DEFAULT_ID)).willReturn(Optional.empty());

        webTestClient.get()
                .uri(DEFAULT_URI_WITH_ID)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isNotFound();

        then(facultyRepository).should(only()).findById(DEFAULT_ID);
    }

    @Test
    void deleteFaculty() {
        webTestClient.delete()
                .uri(DEFAULT_URI_WITH_ID)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk();

        then(facultyRepository).should(only()).deleteById(DEFAULT_ID);
    }

}