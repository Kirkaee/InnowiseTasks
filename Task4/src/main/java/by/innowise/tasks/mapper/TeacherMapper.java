package by.innowise.tasks.mapper;

import by.innowise.tasks.dto.LessonDto;
import by.innowise.tasks.dto.TeacherDto;
import by.innowise.tasks.entity.Lesson;
import by.innowise.tasks.entity.Teacher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TeacherMapper {

    private final LessonMapper lessonMapper;

    public TeacherDto toTeacherDto(Teacher teacher) {
        List<LessonDto> lessons = teacher.getLessons().stream()
                .map(lessonMapper::toStudyHourDto)
                .map(studyHourDto -> (LessonDto) studyHourDto)
                .toList();
        return TeacherDto.builder()
                .id(teacher.getId())
                .fio(teacher.getFio())
                .degree(teacher.getDegree())
                .experience(teacher.getExperience())
                .lessonsDto(lessons)
                .build();
    }

    public Teacher toTeacher(TeacherDto teacherDto) {
        List<Lesson> lessons = teacherDto.getLessonsDto().stream()
                .map(lessonMapper::toStudyHour)
                .map(studyHour -> (Lesson) studyHour)
                .toList();
        return Teacher.builder()
                .id(teacherDto.getId())
                .fio(teacherDto.getFio())
                .degree(teacherDto.getDegree())
                .experience(teacherDto.getExperience())
                .lessons(lessons)
                .build();
    }
}
