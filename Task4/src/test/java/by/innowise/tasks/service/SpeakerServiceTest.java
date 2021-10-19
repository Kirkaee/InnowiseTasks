package by.innowise.tasks.service;

import by.innowise.tasks.dto.SpeakerDto;
import by.innowise.tasks.entity.Speaker;
import by.innowise.tasks.handler.NotFoundException;
import by.innowise.tasks.mapper.SpeakerMapper;
import by.innowise.tasks.repository.SpeakerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static by.innowise.tasks.service.SpeakerService.NOT_FOUND_BY_ID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class SpeakerServiceTest {

    public static final Long DEFAULT_ID = 0L;
    public static final String DEFAULT_FIO = "fio";
    public static final String DEFAULT_TITLE = "title";
    public static final String DEFAULT_MEMBERSHIP = "membership";

    public static final Speaker DEFAULT_SPEAKER = Speaker.builder()
            .id(DEFAULT_ID)
            .fio(DEFAULT_FIO)
            .title(DEFAULT_TITLE)
            .membership(DEFAULT_MEMBERSHIP)
            .build();

    public static final SpeakerDto DEFAULT_SPEAKER_DTO = SpeakerDto.builder()
            .id(DEFAULT_ID)
            .fio(DEFAULT_FIO)
            .title(DEFAULT_TITLE)
            .membership(DEFAULT_MEMBERSHIP)
            .build();

    public static final List<Speaker> DEFAULT_SPEAKERS_LIST = List.of(DEFAULT_SPEAKER);

    @Mock
    private SpeakerRepository speakerRepository;

    @Mock
    private SpeakerMapper speakerMapper;

    @InjectMocks
    private SpeakerService speakerService;

    @Test
    void saveSpeaker() {
        given(speakerRepository.save(DEFAULT_SPEAKER)).willReturn(DEFAULT_SPEAKER);
        given(speakerMapper.toSpeaker(DEFAULT_SPEAKER_DTO)).willReturn(DEFAULT_SPEAKER);
        given(speakerMapper.toSpeakerDto(DEFAULT_SPEAKER)).willReturn(DEFAULT_SPEAKER_DTO);

        assertEquals(DEFAULT_SPEAKER_DTO, speakerService.saveSpeaker(DEFAULT_SPEAKER_DTO));

        then(speakerRepository).should(only()).save(DEFAULT_SPEAKER);
        then(speakerMapper).should(times(1)).toSpeaker(DEFAULT_SPEAKER_DTO);
        then(speakerMapper).should(times(1)).toSpeakerDto(DEFAULT_SPEAKER);
        then(speakerMapper).shouldHaveNoMoreInteractions();
    }

    @Test
    void getSpeakers() {
        given(speakerRepository.findAll()).willReturn(DEFAULT_SPEAKERS_LIST);
        given(speakerMapper.toSpeakerDto(DEFAULT_SPEAKER)).willReturn(DEFAULT_SPEAKER_DTO);

        assertEquals(DEFAULT_SPEAKER_DTO, speakerService.getAllSpeakers().get(0));

        then(speakerRepository).should(only()).findAll();
        then(speakerMapper).should(only()).toSpeakerDto(DEFAULT_SPEAKER);
    }

    @Test
    void getSpeakerById() {
        given(speakerRepository.findById(DEFAULT_ID)).willReturn(Optional.ofNullable(DEFAULT_SPEAKER));
        given(speakerMapper.toSpeakerDto(DEFAULT_SPEAKER)).willReturn(DEFAULT_SPEAKER_DTO);

        assertEquals(DEFAULT_SPEAKER_DTO, speakerService.getSpeakerById(DEFAULT_ID));

        then(speakerRepository).should(only()).findById(DEFAULT_ID);
        then(speakerMapper).should(only()).toSpeakerDto(DEFAULT_SPEAKER);
    }

    @Test
    void deleteSpeaker() {
        speakerService.deleteSpeaker(DEFAULT_ID);

        then(speakerRepository).should(only()).deleteById(DEFAULT_ID);
    }

    @Test
    void updateSpeaker() {
        given(speakerRepository.save(DEFAULT_SPEAKER)).willReturn(DEFAULT_SPEAKER);
        given(speakerMapper.toSpeaker(DEFAULT_SPEAKER_DTO)).willReturn(DEFAULT_SPEAKER);
        given(speakerMapper.toSpeakerDto(DEFAULT_SPEAKER)).willReturn(DEFAULT_SPEAKER_DTO);

        assertEquals(DEFAULT_SPEAKER_DTO, speakerService.updateSpeaker(DEFAULT_ID, DEFAULT_SPEAKER_DTO));

        then(speakerRepository).should(only()).save(DEFAULT_SPEAKER);
        then(speakerMapper).should(times(1)).toSpeaker(DEFAULT_SPEAKER_DTO);
        then(speakerMapper).should(times(1)).toSpeakerDto(DEFAULT_SPEAKER);
        then(speakerMapper).shouldHaveNoMoreInteractions();
    }

    @Test
    void getSpeakerByIdNotFoundException() {

        given(speakerRepository.findById(DEFAULT_ID)).willReturn(Optional.empty());

        NotFoundException notFoundException = assertThrows(NotFoundException.class,
                () -> speakerService.getSpeakerById(DEFAULT_ID));
        assertEquals(String.format(NOT_FOUND_BY_ID, DEFAULT_ID), notFoundException.getMessage());

        then(speakerRepository).should(only()).findById(DEFAULT_ID);
    }
}