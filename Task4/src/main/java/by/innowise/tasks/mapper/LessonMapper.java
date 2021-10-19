package by.innowise.tasks.mapper;

import by.innowise.tasks.dto.LessonDto;
import by.innowise.tasks.entity.Lesson;
import org.springframework.stereotype.Component;

@Component
public class LessonMapper {

    public LessonDto toLessonDto(Lesson lesson) {
        return LessonDto.builder()
                .id(lesson.getId())
                .classDate(lesson.getClassDate())
                .type(lesson.getType())
                .build();
    }

    public Lesson toLesson(LessonDto lessonDto) {
        return Lesson.builder()
                .id(lessonDto.getId())
                .classDate(lessonDto.getClassDate())
                .type(lessonDto.getType())
                .build();
    }
}
