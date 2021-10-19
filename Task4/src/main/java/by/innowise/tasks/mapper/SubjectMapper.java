package by.innowise.tasks.mapper;

import by.innowise.tasks.dto.SubjectDto;
import by.innowise.tasks.entity.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SubjectMapper {

    @Autowired
    private LessonMapper lessonMapper;

    public SubjectDto toSubjectDto(Subject subject) {
        return SubjectDto.builder()
                .id(subject.getId())
                .name(subject.getName())
                .lessonsDto(subject.getLessons().stream()
                        .map(lesson -> lessonMapper.toLessonDto(lesson))
                        .toList())
                .build();
    }

    public Subject toSubject(SubjectDto subjectDto) {
        return Subject.builder()
                .id(subjectDto.getId())
                .name(subjectDto.getName())
                .lessons(subjectDto.getLessonsDto().stream()
                        .map(lessonDto -> lessonMapper.toLesson(lessonDto))
                        .toList())
                .build();
    }
}
