package by.innowise.tasks.service;

import by.innowise.tasks.dto.TeacherDto;
import by.innowise.tasks.handler.NotFoundException;
import by.innowise.tasks.mapper.TeacherMapper;
import by.innowise.tasks.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherService {

    public static final String NOT_FOUND_BY_ID = "There is no such teacher with id: %s";

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private TeacherMapper teacherMapper;

    public TeacherDto saveTeacher(TeacherDto teacherDto) {
        return teacherMapper
                .toTeacherDto(teacherRepository
                        .save(teacherMapper
                                .toTeacher(teacherDto)));
    }

    public List<TeacherDto> getTeachers() {
        return teacherRepository
                .findAll()
                .stream()
                .map(teacherMapper::toTeacherDto)
                .toList();
    }

    public TeacherDto getTeacherById(Long id) {
        return teacherRepository
                .findById(id)
                .map(teacherMapper::toTeacherDto)
                .orElseThrow(() -> new NotFoundException(String.format(NOT_FOUND_BY_ID, id)));
    }

    public void deleteTeacher(Long id) {
        teacherRepository
                .deleteById(id);
    }

    public TeacherDto updateTeacher(Long id, TeacherDto teacherDto) {
        teacherDto.setId(id);
        return teacherMapper
                .toTeacherDto(teacherRepository
                        .save(teacherMapper
                                .toTeacher(teacherDto)));
    }
}
