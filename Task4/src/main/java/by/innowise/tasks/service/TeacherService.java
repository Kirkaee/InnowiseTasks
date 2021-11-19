package by.innowise.tasks.service;

import by.innowise.tasks.dto.TeacherDto;
import by.innowise.tasks.handler.NotFoundException;
import by.innowise.tasks.mapper.TeacherMapper;
import by.innowise.tasks.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TeacherService {

    public static final String NOT_FOUND_BY_ID = "There is no such teacher with id: %s";

    private final TeacherRepository teacherRepository;
    private final TeacherMapper teacherMapper;

    public TeacherDto saveTeacher(TeacherDto teacherDto) {
        return teacherMapper
                .toTeacherDto(teacherRepository
                        .saveAndFlush(teacherMapper
                                .toTeacher(teacherDto)));
    }

    @Transactional(readOnly = true)
    public List<TeacherDto> getAllTeachers() {
        return teacherRepository.getAll()
                .map(teacherMapper::toTeacherDto)
                .toList();
    }

    public TeacherDto getTeacherById(Long id) {
        return teacherRepository.findById(id)
                .map(teacherMapper::toTeacherDto)
                .orElseThrow(() -> new NotFoundException(String.format(NOT_FOUND_BY_ID, id)));
    }

    public void deleteTeacher(Long id) {
        teacherRepository.deleteById(id);
    }

    public void updateTeacher(TeacherDto teacherDto) {
       teacherRepository.save(teacherMapper.toTeacher(teacherDto));
    }
}
