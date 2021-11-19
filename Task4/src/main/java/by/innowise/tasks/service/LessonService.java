package by.innowise.tasks.service;

import by.innowise.tasks.dto.LessonDto;
import by.innowise.tasks.dto.StudyHourDto;
import by.innowise.tasks.entity.Lesson;
import by.innowise.tasks.handler.NotFoundException;
import by.innowise.tasks.mapper.LessonMapper;
import by.innowise.tasks.repository.StudyHourRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LessonService  extends StudyHourService{

    public static final String NOT_FOUND_BY_ID = "There is no such lesson with id: %s";

    @Autowired
    private StudyHourRepository studyHourRepository;
    @Autowired
    private LessonMapper lessonMapper;

    public StudyHourDto save(StudyHourDto studyHourDto) {
        return lessonMapper
                .toStudyHourDto(studyHourRepository
                        .saveAndFlush(lessonMapper
                                .toStudyHour((LessonDto) studyHourDto)));
    }

    public void update(StudyHourDto studyHourDto) {
       studyHourRepository.save(lessonMapper.toStudyHour((LessonDto) studyHourDto));
    }

    @Transactional(readOnly = true)
    public List<StudyHourDto> getAllStudyHours() {
        return studyHourRepository.getAll()
                .filter(n -> n instanceof Lesson)
                .map(n -> lessonMapper.toStudyHourDto((Lesson) n))
                .toList();
    }

    public StudyHourDto getStudyHourById(Long id) {
        return studyHourRepository.findById(id).stream()
                .filter(n -> n instanceof Lesson)
                .map(n -> lessonMapper.toStudyHourDto((Lesson) n))
                .findFirst()
                .orElseThrow(() -> new NotFoundException(String.format(NOT_FOUND_BY_ID, id)));
    }

    public void deleteLesson(Long id) {
        studyHourRepository.deleteById(id);
    }

}
