package by.innowise.tasks.mapper;

import by.innowise.tasks.dto.EventDto;
import by.innowise.tasks.dto.LessonDto;
import by.innowise.tasks.dto.StudyHourDto;
import by.innowise.tasks.entity.Event;
import by.innowise.tasks.entity.Lesson;
import by.innowise.tasks.entity.StudyHour;
import by.innowise.tasks.handler.UnknownClassException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StudyHourMapper {

    @Autowired
    private EventMapper eventMapper;

    @Autowired
    private LessonMapper lessonMapper;

    public StudyHourDto toStudyHourDto(StudyHour studyHour) {

        if (studyHour instanceof Event) {
            return eventMapper.toEventDto((Event) studyHour);
        }
        if (studyHour instanceof Lesson) {
            return lessonMapper.toLessonDto((Lesson) studyHour);
        }
        throw new UnknownClassException();
    }

    public StudyHour toStudyHour(StudyHourDto studyHourDto) {
        if (studyHourDto instanceof EventDto) {
            return eventMapper.toEvent((EventDto) studyHourDto);
        }
        if (studyHourDto instanceof LessonDto) {
            return lessonMapper.toLesson((LessonDto) studyHourDto);
        }
        throw new UnknownClassException();
    }
}
