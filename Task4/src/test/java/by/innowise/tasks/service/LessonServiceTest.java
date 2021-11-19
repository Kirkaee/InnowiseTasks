package by.innowise.tasks.service;

import by.innowise.tasks.dto.LessonDto;
import by.innowise.tasks.entity.Lesson;
import by.innowise.tasks.entity.StudyHour;
import by.innowise.tasks.handler.NotFoundException;
import by.innowise.tasks.mapper.LessonMapper;
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

import static by.innowise.tasks.service.LessonService.NOT_FOUND_BY_ID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class LessonServiceTest {

    public static final Long DEFAULT_ID = 1L;
    public static final Timestamp DEFAULT_CLASS_DATE = Timestamp.valueOf("2017-06-22 19:10:25");
    public static final String DEFAULT_TYPE = "lesson";

    public static final Lesson DEFAULT_LESSON = Lesson.builder()
            .id(DEFAULT_ID)
            .classDate(DEFAULT_CLASS_DATE)
            .type(DEFAULT_TYPE)
            .build();

    public static final LessonDto DEFAULT_LESSON_DTO = LessonDto.builder()
            .id(DEFAULT_ID)
            .classDate(DEFAULT_CLASS_DATE)
            .type(DEFAULT_TYPE)
            .build();

    public static final List<StudyHour> DEFAULT_LESSONS_LIST = List.of(DEFAULT_LESSON);

    @Mock
    private StudyHourRepository studyHourRepository;

    @Mock
    private LessonMapper lessonMapper;

    @InjectMocks
    private LessonService lessonService;



    @Test
    void saveLesson() {
        given(studyHourRepository.saveAndFlush(DEFAULT_LESSON)).willReturn(DEFAULT_LESSON);
        given(lessonMapper.toStudyHour(DEFAULT_LESSON_DTO)).willReturn(DEFAULT_LESSON);
        given(lessonMapper.toStudyHourDto(DEFAULT_LESSON)).willReturn(DEFAULT_LESSON_DTO);

        assertEquals(DEFAULT_LESSON_DTO, lessonService.save(DEFAULT_LESSON_DTO));

        then(studyHourRepository).should(only()).saveAndFlush(DEFAULT_LESSON);
        then(lessonMapper).should(times(1)).toStudyHour(DEFAULT_LESSON_DTO);
        then(lessonMapper).should(times(1)).toStudyHourDto(DEFAULT_LESSON);
        then(lessonMapper).shouldHaveNoMoreInteractions();
    }

    @Test
    void getAllLessons() {
        given(studyHourRepository.getAll()).willReturn(Stream.ofNullable(DEFAULT_LESSON));
        given(lessonMapper.toStudyHourDto(DEFAULT_LESSON)).willReturn(DEFAULT_LESSON_DTO);

        assertEquals(DEFAULT_LESSON_DTO, lessonService.getAllStudyHours().get(0));

        then(studyHourRepository).should(only()).getAll();
        then(lessonMapper).should(only()).toStudyHourDto(DEFAULT_LESSON);
    }

    @Test
    void getLessonById() {
        given(studyHourRepository.findById(DEFAULT_ID)).willReturn(Optional.ofNullable(DEFAULT_LESSON));
        given(lessonMapper.toStudyHourDto(DEFAULT_LESSON)).willReturn(DEFAULT_LESSON_DTO);

        assertEquals(DEFAULT_LESSON_DTO, lessonService.getStudyHourById(DEFAULT_ID));

        then(studyHourRepository).should(only()).findById(DEFAULT_ID);
        then(lessonMapper).should(only()).toStudyHourDto(DEFAULT_LESSON);
    }

    @Test
    void deleteLesson() {
        lessonService.deleteLesson(DEFAULT_ID);

        then(studyHourRepository).should(only()).deleteById(DEFAULT_ID);
    }

    @Test
    void updateLesson() {
        given(studyHourRepository.save(DEFAULT_LESSON)).willReturn(DEFAULT_LESSON);
        given(lessonMapper.toStudyHour(DEFAULT_LESSON_DTO)).willReturn(DEFAULT_LESSON);

        lessonService.update(DEFAULT_LESSON_DTO);

        then(studyHourRepository).should(only()).save(DEFAULT_LESSON);
        then(lessonMapper).should(only()).toStudyHour(DEFAULT_LESSON_DTO);
    }

    @Test
    void getLessonByIdNotFoundException() {

        given(studyHourRepository.findById(DEFAULT_ID)).willReturn(Optional.empty());

        NotFoundException notFoundException = assertThrows(NotFoundException.class,
                () -> lessonService.getStudyHourById(DEFAULT_ID));
        assertEquals(String.format(NOT_FOUND_BY_ID, DEFAULT_ID), notFoundException.getMessage());

        then(studyHourRepository).should(only()).findById(DEFAULT_ID);
    }
}