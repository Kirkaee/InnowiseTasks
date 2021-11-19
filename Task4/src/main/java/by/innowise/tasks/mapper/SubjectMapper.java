package by.innowise.tasks.mapper;

import by.innowise.tasks.dto.LessonDto;
import by.innowise.tasks.dto.SubjectDto;
import by.innowise.tasks.entity.Lesson;
import by.innowise.tasks.entity.Subject;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SubjectMapper {

    private final LessonMapper lessonMapper;

    public SubjectDto toSubjectDto(Subject subject) {
        List<LessonDto> lessons = subject.getLessons().stream()
                .map(lessonMapper::toStudyHourDto)
                .map(studyHourDto -> (LessonDto) studyHourDto)
                .toList();
        return SubjectDto.builder()
                .id(subject.getId())
                .name(subject.getName())
                .lessonsDto(lessons)
                .build();
    }

    public Subject toSubject(SubjectDto subjectDto) {
        List<Lesson> lessons = subjectDto.getLessonsDto().stream()
                .map(lessonMapper::toStudyHour)
                .map(studyHour -> (Lesson) studyHour)
                .toList();
        return Subject.builder()
                .id(subjectDto.getId())
                .name(subjectDto.getName())
                .lessons(lessons)
                .build();
    }
}
