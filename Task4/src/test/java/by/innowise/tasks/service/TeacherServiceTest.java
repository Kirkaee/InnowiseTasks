package by.innowise.tasks.service;

import by.innowise.tasks.dto.TeacherDto;
import by.innowise.tasks.entity.Teacher;
import by.innowise.tasks.handler.NotFoundException;
import by.innowise.tasks.mapper.TeacherMapper;
import by.innowise.tasks.repository.TeacherRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static by.innowise.tasks.service.TeacherService.NOT_FOUND_BY_ID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class TeacherServiceTest {

    public static final Long DEFAULT_ID = 0L;
    public static final String DEFAULT_FIO = "fio";
    public static final String DEFAULT_DEGREE = "degree";
    public static final Integer DEFAULT_EXPERIENCE = 1;

    public static final Teacher DEFAULT_TEACHER = Teacher.builder()
            .id(DEFAULT_ID)
            .fio(DEFAULT_FIO)
            .degree(DEFAULT_DEGREE)
            .experience(DEFAULT_EXPERIENCE)
            .build();

    public static final TeacherDto DEFAULT_TEACHER_DTO = TeacherDto.builder()
            .id(DEFAULT_ID)
            .fio(DEFAULT_FIO)
            .degree(DEFAULT_DEGREE)
            .experience(DEFAULT_EXPERIENCE)
            .build();

    @Mock
    private TeacherRepository teacherRepository;

    @Mock
    private TeacherMapper teacherMapper;

    @InjectMocks
    private TeacherService teacherService;

    @Test
    void saveTeacher() {
        given(teacherRepository.save(DEFAULT_TEACHER)).willReturn(DEFAULT_TEACHER);
        given(teacherMapper.toTeacher(DEFAULT_TEACHER_DTO)).willReturn(DEFAULT_TEACHER);
        given(teacherMapper.toTeacherDto(DEFAULT_TEACHER)).willReturn(DEFAULT_TEACHER_DTO);

        assertEquals(DEFAULT_TEACHER_DTO, teacherService.saveTeacher(DEFAULT_TEACHER_DTO));

        then(teacherRepository).should(only()).save(DEFAULT_TEACHER);
        then(teacherMapper).should(times(1)).toTeacher(DEFAULT_TEACHER_DTO);
        then(teacherMapper).should(times(1)).toTeacherDto(DEFAULT_TEACHER);
        then(teacherMapper).shouldHaveNoMoreInteractions();
    }

    @Test
    void getTeachers() {
        List<Teacher> faculty = List.of(DEFAULT_TEACHER);
        given(teacherRepository.findAll()).willReturn(faculty);
        given(teacherMapper.toTeacherDto(DEFAULT_TEACHER)).willReturn(DEFAULT_TEACHER_DTO);

        assertEquals(DEFAULT_TEACHER_DTO, teacherService.getTeachers().get(0));

        then(teacherRepository).should(only()).findAll();
        then(teacherMapper).should(times(1)).toTeacherDto(DEFAULT_TEACHER);
        then(teacherMapper).shouldHaveNoMoreInteractions();
    }

    @Test
    void getTeacherById() {
        given(teacherRepository.findById(DEFAULT_ID)).willReturn(Optional.ofNullable(DEFAULT_TEACHER));
        given(teacherMapper.toTeacherDto(DEFAULT_TEACHER)).willReturn(DEFAULT_TEACHER_DTO);

        assertEquals(DEFAULT_TEACHER_DTO, teacherService.getTeacherById(DEFAULT_ID));

        then(teacherRepository).should(only()).findById(DEFAULT_ID);
        then(teacherMapper).should(times(1)).toTeacherDto(DEFAULT_TEACHER);
        then(teacherMapper).shouldHaveNoMoreInteractions();
    }

    @Test
    void deleteTeacher() {
        teacherService.deleteTeacher(DEFAULT_ID);

        teacherRepository.deleteById(DEFAULT_ID);
    }

    @Test
    void updateTeacher() {
        given(teacherRepository.save(DEFAULT_TEACHER)).willReturn(DEFAULT_TEACHER);
        given(teacherMapper.toTeacher(DEFAULT_TEACHER_DTO)).willReturn(DEFAULT_TEACHER);
        given(teacherMapper.toTeacherDto(DEFAULT_TEACHER)).willReturn(DEFAULT_TEACHER_DTO);

        assertEquals(DEFAULT_TEACHER_DTO, teacherService.updateTeacher(DEFAULT_ID, DEFAULT_TEACHER_DTO));

        then(teacherRepository).should(only()).save(DEFAULT_TEACHER);
        then(teacherMapper).should(times(1)).toTeacher(DEFAULT_TEACHER_DTO);
        then(teacherMapper).should(times(1)).toTeacherDto(DEFAULT_TEACHER);
        then(teacherMapper).shouldHaveNoMoreInteractions();
    }

    @Test
    void getDepartmentByIdNotFoundException() {

        given(teacherRepository.findById(DEFAULT_ID)).willReturn(Optional.empty());

        NotFoundException notFoundException = assertThrows(NotFoundException.class,
                () -> teacherService.getTeacherById(DEFAULT_ID));
        assertEquals(String.format(NOT_FOUND_BY_ID, DEFAULT_ID), notFoundException.getMessage());

        then(teacherRepository).should(only()).findById(DEFAULT_ID);
    }
}