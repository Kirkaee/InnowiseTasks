package by.innowise.tasks.service;

import by.innowise.tasks.dto.EventDto;
import by.innowise.tasks.entity.Event;
import by.innowise.tasks.entity.StudyHour;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class EventServiceTest {

    public static final Long DEFAULT_ID = 0L;
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

    @Mock
    private StudyHourRepository studyHourRepository;

    @Mock
    private EventMapper eventMapper;

    @InjectMocks
    private EventService eventService;


    @Test
    void saveEvent() {
        given(studyHourRepository.save(DEFAULT_EVENT)).willReturn(DEFAULT_EVENT);
        given(eventMapper.toEvent(DEFAULT_EVENT_DTO)).willReturn(DEFAULT_EVENT);
        given(eventMapper.toEventDto(DEFAULT_EVENT)).willReturn(DEFAULT_EVENT_DTO);

        assertEquals(DEFAULT_EVENT_DTO, eventService.saveEvent(DEFAULT_EVENT_DTO));

        then(studyHourRepository).should(only()).save(DEFAULT_EVENT);
        then(eventMapper).should(times(1)).toEvent(DEFAULT_EVENT_DTO);
        then(eventMapper).should(times(1)).toEventDto(DEFAULT_EVENT);
        then(eventMapper).shouldHaveNoMoreInteractions();
    }

    @Test
    void getEvents() {
        List<StudyHour> events = List.of(DEFAULT_EVENT);
        given(studyHourRepository.findAll()).willReturn(events);
        given(eventMapper.toEventDto(DEFAULT_EVENT)).willReturn(DEFAULT_EVENT_DTO);

        assertEquals(DEFAULT_EVENT_DTO, eventService.getEvents().get(0));

        then(studyHourRepository).should(only()).findAll();
        then(eventMapper).should(times(1)).toEventDto(DEFAULT_EVENT);
        then(eventMapper).shouldHaveNoMoreInteractions();
    }

    @Test
    void getEventById() {
        given(studyHourRepository.findById(DEFAULT_ID)).willReturn(Optional.ofNullable(DEFAULT_EVENT));
        given(eventMapper.toEventDto(DEFAULT_EVENT)).willReturn(DEFAULT_EVENT_DTO);

        assertEquals(DEFAULT_EVENT_DTO, eventService.getEventById(DEFAULT_ID));

        then(studyHourRepository).should(only()).findById(DEFAULT_ID);
        then(eventMapper).should(times(1)).toEventDto(DEFAULT_EVENT);
        then(eventMapper).shouldHaveNoMoreInteractions();
    }

    @Test
    void deleteEvent() {
        eventService.deleteEvent(DEFAULT_ID);

        then(studyHourRepository).should(only()).deleteById(DEFAULT_ID);
    }

    @Test
    void updateEvent() {
        given(studyHourRepository.save(DEFAULT_EVENT)).willReturn(DEFAULT_EVENT);
        given(eventMapper.toEvent(DEFAULT_EVENT_DTO)).willReturn(DEFAULT_EVENT);
        given(eventMapper.toEventDto(DEFAULT_EVENT)).willReturn(DEFAULT_EVENT_DTO);

        assertEquals(DEFAULT_EVENT_DTO, eventService.updateEvent(DEFAULT_ID, DEFAULT_EVENT_DTO));

        then(studyHourRepository).should(only()).save(DEFAULT_EVENT);
        then(eventMapper).should(times(1)).toEvent(DEFAULT_EVENT_DTO);
        then(eventMapper).should(times(1)).toEventDto(DEFAULT_EVENT);
        then(eventMapper).shouldHaveNoMoreInteractions();
    }
}