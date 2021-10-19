package by.innowise.tasks.service;

import by.innowise.tasks.dto.EventDto;
import by.innowise.tasks.dto.LessonDto;
import by.innowise.tasks.dto.StudyHourDto;
import by.innowise.tasks.entity.Event;
import by.innowise.tasks.entity.Lesson;
import by.innowise.tasks.handler.NotFoundException;
import by.innowise.tasks.handler.UnknownClassException;
import by.innowise.tasks.mapper.StudyHourMapper;
import by.innowise.tasks.repository.StudyHourRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudyHourService {

    public static final String NOT_FOUND_BY_ID = "There is no such study hour with id: %s";

    @Autowired
    private StudyHourRepository studyHourRepository;

    @Autowired
    private StudyHourMapper studyHourMapper;

    @Autowired
    private EventService eventService;

    @Autowired
    private LessonService lessonService;

    public StudyHourDto saveStudyHour(StudyHourDto studyHourDto) {
        if (studyHourDto instanceof EventDto) {
            return eventService.saveEvent((EventDto) studyHourDto);
        }
        if (studyHourDto instanceof LessonDto) {
            return lessonService.saveLesson((LessonDto) studyHourDto);
        }
        throw new UnknownClassException();
    }

    public StudyHourDto updateStudyHour(Long id, StudyHourDto studyHourDto) {
        if (studyHourDto instanceof EventDto) {
            if (getStudyHourById(id) instanceof LessonDto) {
                return changeLessonToEvent(id, studyHourDto);
            }
            return eventService.updateEvent(id, (EventDto) studyHourDto);
        }

        if (studyHourDto instanceof LessonDto) {
            if (getStudyHourById(id) instanceof EventDto) {
                return changeEventToLesson(id, studyHourDto);
            }
            return lessonService.updateLesson(id, (LessonDto) studyHourDto);
        }
        throw new UnknownClassException();
    }

    public List<StudyHourDto> getStudyHours() {
        return studyHourRepository.findAll().stream()
                .map(studyHourMapper::toStudyHourDto).toList();
    }

    public StudyHourDto getStudyHourById(Long id) {
        return studyHourRepository.findById(id)
                .map(studyHourMapper::toStudyHourDto)
                .orElseThrow(() -> new NotFoundException(String.format(NOT_FOUND_BY_ID, id)));
    }

    public void deleteStudyHour(Long id) {
        studyHourRepository
                .deleteById(id);
    }

    public StudyHourDto changeLessonToEvent(Long id, StudyHourDto studyHourDto) {
        studyHourRepository.deleteById(id);
        studyHourDto.setId(id);
        return eventService.saveEvent((EventDto) studyHourDto);
    }

    public StudyHourDto changeEventToLesson(Long id, StudyHourDto studyHourDto) {
        studyHourRepository.deleteById(id);
        studyHourDto.setId(id);
        return lessonService.saveLesson((LessonDto) studyHourDto);
    }


}
