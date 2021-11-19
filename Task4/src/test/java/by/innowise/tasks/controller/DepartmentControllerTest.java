package by.innowise.tasks.controller;

import by.innowise.tasks.Application;
import by.innowise.tasks.entity.Department;
import by.innowise.tasks.entity.Teacher;
import by.innowise.tasks.repository.DepartmentRepository;
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
import java.util.stream.Stream;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.only;

@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableAutoConfiguration
@AutoConfigureWebTestClient
@ActiveProfiles("test")
class DepartmentControllerTest {

    public static final String DEFAULT_PATH = "/json/departmentDto.json";
    public static final String DEFAULT_URI = "/department";
    public static final String DEFAULT_URI_WITH_ID = "/department/1";
    public static final Long DEFAULT_ID = 1L;
    public static final String DEFAULT_NAME = "departmentName";
    public static final Teacher DEFAULT_TEACHER = Teacher.builder()
            .fio("fio")
            .degree("degree")
            .experience(5)
            .lessons(new ArrayList<>())
            .build();
    public static final List<Teacher> DEFAULT_TEACHERS = List.of(DEFAULT_TEACHER);

    public static final Department DEFAULT_DEPARTMENT = Department.builder()
            .id(DEFAULT_ID)
            .name(DEFAULT_NAME)
            .teachers(DEFAULT_TEACHERS)
            .build();

    @MockBean
    private DepartmentRepository departmentRepository;

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void postDepartment() {
        given(departmentRepository.saveAndFlush(DEFAULT_DEPARTMENT)).willReturn(DEFAULT_DEPARTMENT);

        webTestClient.post()
                .uri(DEFAULT_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(Objects.requireNonNull(JsonReader.readJson(DEFAULT_PATH)))
                .exchange()
                .expectStatus()
                .isOk();

        then(departmentRepository).should(only()).saveAndFlush(DEFAULT_DEPARTMENT);
    }

    @Test
    void putDepartment() {
        given(departmentRepository.save(DEFAULT_DEPARTMENT)).willReturn(DEFAULT_DEPARTMENT);

        webTestClient.put()
                .uri(DEFAULT_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(Objects.requireNonNull(JsonReader.readJson(DEFAULT_PATH)))
                .exchange()
                .expectStatus()
                .isOk();

        then(departmentRepository).should(only()).save(DEFAULT_DEPARTMENT);
    }

    @Test
    void getDepartments() {
        given(departmentRepository.getAll()).willReturn(Stream.ofNullable(DEFAULT_DEPARTMENT));

        webTestClient.get()
                .uri(DEFAULT_URI)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk();

        then(departmentRepository).should(only()).getAll();
    }

    @Test
    void getDepartmentById() {
        given(departmentRepository.findById(DEFAULT_ID)).willReturn(Optional.ofNullable(DEFAULT_DEPARTMENT));

        webTestClient.get()
                .uri(DEFAULT_URI_WITH_ID)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk();

        then(departmentRepository).should(only()).findById(DEFAULT_ID);
    }

    @Test
    void getDepartmentByIdNotFoundException() {
        given(departmentRepository.findById(DEFAULT_ID)).willReturn(Optional.empty());

        webTestClient.get()
                .uri(DEFAULT_URI_WITH_ID)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isNotFound();

        then(departmentRepository).should(only()).findById(DEFAULT_ID);
    }

    @Test
    void deleteDepartment() {
        webTestClient.delete()
                .uri(DEFAULT_URI_WITH_ID)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk();

        then(departmentRepository).should(only()).deleteById(DEFAULT_ID);
    }


}