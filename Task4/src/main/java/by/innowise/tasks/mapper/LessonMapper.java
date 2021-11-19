package by.innowise.tasks.mapper;

import by.innowise.tasks.dto.LessonDto;
import by.innowise.tasks.dto.StudyHourDto;
import by.innowise.tasks.entity.Lesson;
import by.innowise.tasks.entity.StudyHour;
import org.springframework.stereotype.Component;

@Component
public class LessonMapper extends StudyHourMapper{

    @Override
    public StudyHourDto toStudyHourDto(StudyHour studyHour) {
        Lesson lesson = (Lesson)  studyHour;
        return LessonDto.builder()
                .id(lesson.getId())
                .classDate(lesson.getClassDate())
                .type(lesson.getType())
                .build();
    }

    @Override
    public StudyHour toStudyHour(StudyHourDto studyHourDto) {
        LessonDto lessonDto = (LessonDto) studyHourDto;
        return Lesson.builder()
                .id(lessonDto.getId())
                .classDate(lessonDto.getClassDate())
                .type(lessonDto.getType())
                .build();
    }
}
