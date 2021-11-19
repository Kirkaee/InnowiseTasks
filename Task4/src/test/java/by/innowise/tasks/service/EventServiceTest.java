package by.innowise.tasks.service;

import by.innowise.tasks.dto.EventDto;
import by.innowise.tasks.entity.Event;
import by.innowise.tasks.entity.StudyHour;
import by.innowise.tasks.handler.NotFoundException;
import by.innowise.tasks.mapper.EventMapper;
import by.innowise.tasks.repository.StudyHourRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static by.innowise.tasks.service.EventService.NOT_FOUND_BY_ID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class EventServiceTest {

    public static final Long DEFAULT_ID = 1L;
    public static final Timestamp DEFAULT_CLASS_DATE = Timestamp.valueOf("2017-06-22 19:10:25");
    public static final String DEFAULT_TYPE = "event";
    public static final String DEFAULT_TOPIC = "topic";

    public static final Event DEFAULT_EVENT = Event.builder()
            .id(DEFAULT_ID)
            .classDate(DEFAULT_CLASS_DATE)
            .type(DEFAULT_TYPE)
            .topic(DEFAULT_TOPIC)
            .build();

    public static final EventDto DEFAULT_EVENT_DTO = EventDto.builder()
            .id(DEFAULT_ID)
            .classDate(DEFAULT_CLASS_DATE)
            .type(DEFAULT_TYPE)
            .topic(DEFAULT_TOPIC)
            .build();

    public static final List<StudyHour> DEFAULT_EVENTS_LIST = List.of(DEFAULT_EVENT);

    @Mock
    private StudyHourRepository studyHourRepository;

    @Mock
    private EventMapper eventMapper;

    @InjectMocks
    private EventService eventService;


    @Test
    void saveEvent() {
        given(studyHourRepository.saveAndFlush(DEFAULT_EVENT)).willReturn(DEFAULT_EVENT);
        given(eventMapper.toStudyHour(DEFAULT_EVENT_DTO)).willReturn(DEFAULT_EVENT);
        given(eventMapper.toStudyHourDto(DEFAULT_EVENT)).willReturn(DEFAULT_EVENT_DTO);

        assertEquals(DEFAULT_EVENT_DTO, eventService.save(DEFAULT_EVENT_DTO));

        then(studyHourRepository).should(only()).saveAndFlush(DEFAULT_EVENT);
        then(eventMapper).should(times(1)).toStudyHour(DEFAULT_EVENT_DTO);
        then(eventMapper).should(times(1)).toStudyHourDto(DEFAULT_EVENT);
        then(eventMapper).shouldHaveNoMoreInteractions();
    }

    @Test
    void getAllEvents() {
        given(studyHourRepository.getAll()).willReturn(Stream.ofNullable(DEFAULT_EVENT));
        given(eventMapper.toStudyHourDto(DEFAULT_EVENT)).willReturn(DEFAULT_EVENT_DTO);

        assertEquals(DEFAULT_EVENT_DTO, eventService.getAllStudyHours().get(0));

        then(studyHourRepository).should(only()).getAll();
        then(eventMapper).should(only()).toStudyHourDto(DEFAULT_EVENT);
    }

    @Test
    void getEventById() {
        given(studyHourRepository.findById(DEFAULT_ID)).willReturn(Optional.ofNullable(DEFAULT_EVENT));
        given(eventMapper.toStudyHourDto(DEFAULT_EVENT)).willReturn(DEFAULT_EVENT_DTO);

        assertEquals(DEFAULT_EVENT_DTO, eventService.getStudyHourById(DEFAULT_ID));

        then(studyHourRepository).should(only()).findById(DEFAULT_ID);
        then(eventMapper).should(only()).toStudyHourDto(DEFAULT_EVENT);
    }

    @Test
    void deleteEvent() {
        eventService.deleteEvent(DEFAULT_ID);

        then(studyHourRepository).should(only()).deleteById(DEFAULT_ID);
    }

    @Test
    void updateEvent() {
        given(studyHourRepository.save(DEFAULT_EVENT)).willReturn(DEFAULT_EVENT);
        given(eventMapper.toStudyHour(DEFAULT_EVENT_DTO)).willReturn(DEFAULT_EVENT);

        eventService.update(DEFAULT_EVENT_DTO);

        then(studyHourRepository).should(only()).save(DEFAULT_EVENT);
        then(eventMapper).should(only()).toStudyHour(DEFAULT_EVENT_DTO);
    }

    @Test
    void getEventByIdNotFoundException() {

        given(studyHourRepository.findById(DEFAULT_ID)).willReturn(Optional.empty());

        NotFoundException notFoundException = assertThrows(NotFoundException.class,
                () -> eventService.getStudyHourById(DEFAULT_ID));
        assertEquals(String.format(NOT_FOUND_BY_ID, DEFAULT_ID), notFoundException.getMessage());

        then(studyHourRepository).should(only()).findById(DEFAULT_ID);
    }
}