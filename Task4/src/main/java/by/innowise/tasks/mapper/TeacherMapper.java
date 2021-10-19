package by.innowise.tasks.mapper;

import by.innowise.tasks.dto.TeacherDto;
import by.innowise.tasks.entity.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TeacherMapper {

    @Autowired
    private LessonMapper lessonMapper;

    public TeacherDto toTeacherDto(Teacher teacher) {
        return TeacherDto.builder()
                .id(teacher.getId())
                .fio(teacher.getFio())
                .degree(teacher.getDegree())
                .experience(teacher.getExperience())
                .lessonsDto(teacher.getLessons().stream()
                        .map(lesson -> lessonMapper.toLessonDto(lesson))
                        .toList())
                .build();
    }

    public Teacher toTeacher(TeacherDto teacherDto) {
        return Teacher.builder()
                .id(teacherDto.getId())
                .fio(teacherDto.getFio())
                .degree(teacherDto.getDegree())
                .experience(teacherDto.getExperience())
                .lessons(teacherDto.getLessonsDto().stream()
                        .map(lessonDto -> lessonMapper.toLesson(lessonDto))
                        .toList())
                .build();
    }
}
