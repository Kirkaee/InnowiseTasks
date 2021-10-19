package by.innowise.tasks.service;

import by.innowise.tasks.dto.EventDto;
import by.innowise.tasks.dto.LessonDto;
import by.innowise.tasks.entity.Event;
import by.innowise.tasks.entity.Lesson;
import by.innowise.tasks.entity.StudyHour;
import by.innowise.tasks.handler.NotFoundException;
import by.innowise.tasks.mapper.StudyHourMapper;
import by.innowise.tasks.repository.StudyHourRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class StudyHourServiceTest {

    public static final Long DEFAULT_ID = 1L;
    public static final Timestamp DEFAULT_CLASS_DATE = Timestamp.valueOf("2017-06-22 19:10:25");
    public static final String DEFAULT_TYPE_EVENT = "event";
    public static final String DEFAULT_TYPE_LESSON = "lesson";
    public static final String DEFAULT_TITLE = "title";

    public static final Lesson DEFAULT_LESSON = Lesson.builder()
            .id(DEFAULT_ID)
            .classDate(DEFAULT_CLASS_DATE)
            .type(DEFAULT_TYPE_LESSON)
            .build();

    public static final LessonDto DEFAULT_LESSON_DTO = LessonDto.builder()
            .id(DEFAULT_ID)
            .classDate(DEFAULT_CLASS_DATE)
            .type(DEFAULT_TYPE_LESSON)
            .build();

    public static final Event DEFAULT_EVENT = Event.builder()
            .id(DEFAULT_ID)
            .classDate(DEFAULT_CLASS_DATE)
            .type(DEFAULT_TYPE_EVENT)
            .topic(DEFAULT_TITLE)
            .build();

    public static final EventDto DEFAULT_EVENT_DTO = EventDto.builder()
            .id(DEFAULT_ID)
            .classDate(DEFAULT_CLASS_DATE)
            .type(DEFAULT_TYPE_EVENT)
            .topic(DEFAULT_TITLE)
            .build();

    public static final List<StudyHour> DEFAULT_STUDY_HOUR_LIST = List.of(DEFAULT_EVENT);

    @Mock
    private StudyHourRepository studyHourRepository;

    @Mock
    private StudyHourMapper studyHourMapper;

    @Mock
    private LessonService lessonService;

    @Mock
    private EventService eventService;

    @InjectMocks
    private StudyHourService studyHourService;

    @Test
    void saveLesson() {
        given(lessonService.saveLesson(DEFAULT_LESSON_DTO)).willReturn(DEFAULT_LESSON_DTO);

        assertEquals(DEFAULT_LESSON_DTO, studyHourService.saveStudyHour(DEFAULT_LESSON_DTO));

        then(lessonService).should(times(1)).saveLesson(DEFAULT_LESSON_DTO);
    }

    @Test
    void saveEvent() {
        given(eventService.saveEvent(DEFAULT_EVENT_DTO)).willReturn(DEFAULT_EVENT_DTO);

        assertEquals(DEFAULT_EVENT_DTO, studyHourService.saveStudyHour(DEFAULT_EVENT_DTO));

        then(eventService).should(only()).saveEvent(DEFAULT_EVENT_DTO);
    }

    @Test
    void updateEvent() {
        given(studyHourRepository.findById(DEFAULT_ID)).willReturn(Optional.ofNullable(DEFAULT_EVENT));
        given(eventService.updateEvent(DEFAULT_ID, DEFAULT_EVENT_DTO)).willReturn(DEFAULT_EVENT_DTO);
        given(studyHourMapper.toStudyHourDto(DEFAULT_EVENT)).willReturn(DEFAULT_EVENT_DTO);

        assertEquals(DEFAULT_EVENT_DTO, studyHourService.updateStudyHour(DEFAULT_ID, DEFAULT_EVENT_DTO));

        then(studyHourRepository).should(only()).findById(DEFAULT_ID);
        then(eventService).should(only()).updateEvent(DEFAULT_ID, DEFAULT_EVENT_DTO);
        then(studyHourMapper).should(only()).toStudyHourDto(DEFAULT_EVENT);
    }

    @Test
    void updateLesson() {
        given(lessonService.updateLesson(DEFAULT_ID, DEFAULT_LESSON_DTO)).willReturn(DEFAULT_LESSON_DTO);
        given(studyHourRepository.findById(DEFAULT_ID)).willReturn(Optional.ofNullable(DEFAULT_LESSON));
        given(studyHourMapper.toStudyHourDto(DEFAULT_LESSON)).willReturn(DEFAULT_LESSON_DTO);

        assertEquals(DEFAULT_LESSON_DTO, studyHourService.updateStudyHour(DEFAULT_ID, DEFAULT_LESSON_DTO));

        then(lessonService).should(only()).updateLesson(DEFAULT_ID, DEFAULT_LESSON_DTO);
        then(studyHourRepository).should(only()).findById(DEFAULT_ID);
        then(studyHourMapper).should(only()).toStudyHourDto(DEFAULT_LESSON);
    }

    @Test
    void updateLessonToEvent() {
        given(studyHourRepository.findById(DEFAULT_ID)).willReturn(Optional.ofNullable(DEFAULT_LESSON));
        given(eventService.saveEvent(DEFAULT_EVENT_DTO)).willReturn(DEFAULT_EVENT_DTO);
        given(studyHourMapper.toStudyHourDto(DEFAULT_LESSON)).willReturn(DEFAULT_LESSON_DTO);


        assertEquals(DEFAULT_EVENT_DTO, studyHourService.updateStudyHour(DEFAULT_ID, DEFAULT_EVENT_DTO));

        then(studyHourRepository).should(times(1)).findById(DEFAULT_ID);
        then(studyHourRepository).should(times(1)).deleteById(DEFAULT_ID);
        then(studyHourRepository).shouldHaveNoMoreInteractions();
        then(eventService).should(only()).saveEvent(DEFAULT_EVENT_DTO);
        then(studyHourMapper).should(only()).toStudyHourDto(DEFAULT_LESSON);

    }

    @Test
    void updateEventToLesson() {
        given(studyHourRepository.findById(DEFAULT_ID)).willReturn(Optional.ofNullable(DEFAULT_EVENT));
        given(lessonService.saveLesson(DEFAULT_LESSON_DTO)).willReturn(DEFAULT_LESSON_DTO);
        given(studyHourMapper.toStudyHourDto(DEFAULT_EVENT)).willReturn(DEFAULT_EVENT_DTO);

        assertEquals(DEFAULT_LESSON_DTO, studyHourService.updateStudyHour(DEFAULT_ID, DEFAULT_LESSON_DTO));

        then(studyHourRepository).should(times(1)).findById(DEFAULT_ID);
        then(studyHourRepository).should(times(1)).deleteById(DEFAULT_ID);
        then(studyHourRepository).shouldHaveNoMoreInteractions();
        then(lessonService).should(only()).saveLesson(DEFAULT_LESSON_DTO);
        then(studyHourMapper).should(only()).toStudyHourDto(DEFAULT_EVENT);

    }


    @Test
    void getByIdStudyHour() {
        given(studyHourRepository.findById(DEFAULT_ID)).willReturn(Optional.ofNullable(DEFAULT_EVENT));
        given(studyHourMapper.toStudyHourDto(DEFAULT_EVENT)).willReturn(DEFAULT_EVENT_DTO);

        assertEquals(DEFAULT_EVENT_DTO, studyHourService.getStudyHourById(DEFAULT_ID));

        then(studyHourRepository).should(only()).findById(DEFAULT_ID);
        then(studyHourMapper).should(only()).toStudyHourDto(DEFAULT_EVENT);
    }

    @Test
    void getStudyHours() {
        given(studyHourRepository.findAll()).willReturn(DEFAULT_STUDY_HOUR_LIST);
        given(studyHourMapper.toStudyHourDto(DEFAULT_EVENT)).willReturn(DEFAULT_EVENT_DTO);

        assertEquals(DEFAULT_EVENT_DTO, studyHourService.getStudyHours().get(0));

        then(studyHourRepository).should(only()).findAll();
        then(studyHourMapper).should(only()).toStudyHourDto(DEFAULT_EVENT);
    }

    @Test
    void deleteStudyHour() {
        studyHourService.deleteStudyHour(DEFAULT_ID);

        then(studyHourRepository).should(only()).deleteById(DEFAULT_ID);
    }

    @Test
    void getStudyHourByIdNotFoundException() {
        given(studyHourRepository.findById(DEFAULT_ID)).willReturn(Optional.empty());

        NotFoundException notFoundException = assertThrows(NotFoundException.class,
                () -> studyHourService.getStudyHourById(DEFAULT_ID));
        assertEquals(String.format(StudyHourService.NOT_FOUND_BY_ID, DEFAULT_ID), notFoundException.getMessage());

        then(studyHourRepository).should(only()).findById(DEFAULT_ID);
    }
}