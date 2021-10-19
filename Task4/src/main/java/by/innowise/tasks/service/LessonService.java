package by.innowise.tasks.service;

import by.innowise.tasks.dto.LessonDto;
import by.innowise.tasks.entity.Event;
import by.innowise.tasks.entity.Lesson;
import by.innowise.tasks.handler.NotFoundException;
import by.innowise.tasks.mapper.LessonMapper;
import by.innowise.tasks.repository.StudyHourRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LessonService {

    public static final String NOT_FOUND_BY_ID = "There is no such lesson with id: %s";

    @Autowired
    private StudyHourRepository studyHourRepository;

    @Autowired
    private LessonMapper lessonMapper;

    public LessonDto saveLesson(LessonDto lessonDto) {
        return lessonMapper
                .toLessonDto(studyHourRepository
                        .save(lessonMapper
                                .toLesson(lessonDto)));
    }

    public List<LessonDto> getLesson() {
        return studyHourRepository.findAll().stream()
                .filter(n -> n instanceof Lesson)
                .map(n -> lessonMapper.toLessonDto((Lesson) n))
                .toList();
    }

    public LessonDto getLessonById(Long id) {
        return studyHourRepository.findById(id).stream()
                .filter(n -> n instanceof Lesson)
                .map(n -> lessonMapper.toLessonDto((Lesson) n))
                .findFirst().orElseThrow(() -> new NotFoundException(String.format(NOT_FOUND_BY_ID, id)));
    }

    public void deleteLesson(Long id) {
        studyHourRepository
                .deleteById(id);
    }

    public LessonDto updateLesson(Long id, LessonDto lessonDto) {
        lessonDto.setId(id);
        return lessonMapper
                .toLessonDto(studyHourRepository
                        .save(lessonMapper
                                .toLesson(lessonDto)));
    }
}
