package by.innowise.tasks.controller;

import by.innowise.tasks.Application;
import by.innowise.tasks.entity.Speaker;
import by.innowise.tasks.repository.SpeakerRepository;
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
class SpeakerControllerTest {

    public static final String DEFAULT_PATH = "/json/speakerDto.json";
    public static final String DEFAULT_URI = "/speaker";
    public static final String DEFAULT_URI_WITH_ID = "/speaker/1";
    public static final Long DEFAULT_ID = 1L;
    public static final String DEFAULT_FIO = "fio";
    public static final String DEFAULT_TITLE = "title";
    public static final String DEFAULT_MEMBERSHIP = "membership";

    public static final Speaker DEFAULT_SPEAKER = Speaker.builder()
            .fio(DEFAULT_FIO)
            .title(DEFAULT_TITLE)
            .membership(DEFAULT_MEMBERSHIP)
            .build();

    @MockBean
    private SpeakerRepository speakerRepository;

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void postSpeaker() {
        DEFAULT_SPEAKER.setId(null);
        given(speakerRepository.save(DEFAULT_SPEAKER)).willReturn(DEFAULT_SPEAKER);

        webTestClient.post()
                .uri(DEFAULT_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(Objects.requireNonNull(JsonReader.readJson(DEFAULT_PATH)))
                .exchange()
                .expectStatus()
                .isOk();

        then(speakerRepository).should(only()).save(DEFAULT_SPEAKER);
    }

    @Test
    void putSpeaker() {
        DEFAULT_SPEAKER.setId(DEFAULT_ID);
        given(speakerRepository.save(DEFAULT_SPEAKER)).willReturn(DEFAULT_SPEAKER);

        webTestClient.put()
                .uri(DEFAULT_URI_WITH_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(Objects.requireNonNull(JsonReader.readJson(DEFAULT_PATH)))
                .exchange()
                .expectStatus()
                .isOk();

        then(speakerRepository).should(only()).save(DEFAULT_SPEAKER);
    }

    @Test
    void getSpeaker() {
        given(speakerRepository.findAll()).willReturn(List.of(DEFAULT_SPEAKER));

        webTestClient.get()
                .uri(DEFAULT_URI)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk();

        then(speakerRepository).should(only()).findAll();
    }

    @Test
    void getSpeakerById() {
        given(speakerRepository.findById(DEFAULT_ID)).willReturn(Optional.ofNullable(DEFAULT_SPEAKER));

        webTestClient.get()
                .uri(DEFAULT_URI_WITH_ID)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk();

        then(speakerRepository).should(only()).findById(DEFAULT_ID);
    }

    @Test
    void getSpeakerByIdNotFoundException() {
        given(speakerRepository.findById(DEFAULT_ID)).willReturn(Optional.empty());

        webTestClient.get()
                .uri(DEFAULT_URI_WITH_ID)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isNotFound();

        then(speakerRepository).should(only()).findById(DEFAULT_ID);
    }

    @Test
    void deleteSpeaker() {
        webTestClient.delete()
                .uri(DEFAULT_URI_WITH_ID)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk();

        then(speakerRepository).should(only()).deleteById(DEFAULT_ID);
    }


}