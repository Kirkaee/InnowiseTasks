package by.innowise.tasks.service;

import by.innowise.tasks.dto.DepartmentDto;
import by.innowise.tasks.dto.FacultyDto;
import by.innowise.tasks.entity.Department;
import by.innowise.tasks.entity.Faculty;
import by.innowise.tasks.handler.NotFoundException;
import by.innowise.tasks.mapper.FacultyMapper;
import by.innowise.tasks.repository.FacultyRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static by.innowise.tasks.service.FacultyService.NOT_FOUND_BY_ID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class FacultyServiceTest {

    public static final Long DEFAULT_ID = 0L;
    public static final String DEFAULT_NAME = "name";
    public static final List<Department> DEFAULT_DEPARTMENTS = new ArrayList<>();
    public static final List<DepartmentDto> DEFAULT_DEPARTMENTS_DTO = new ArrayList<>();

    public static final Faculty DEFAULT_FACULTY = Faculty.builder()
            .id(DEFAULT_ID)
            .name(DEFAULT_NAME)
            .departments(DEFAULT_DEPARTMENTS)
            .build();

    public static final FacultyDto DEFAULT_FACULTY_DTO = FacultyDto.builder()
            .id(DEFAULT_ID)
            .name(DEFAULT_NAME)
            .departmentsDto(DEFAULT_DEPARTMENTS_DTO)
            .build();

    @Mock
    private FacultyMapper facultyMapper;

    @Mock
    private FacultyRepository facultyRepository;

    @InjectMocks
    private FacultyService facultyService;

    @Test
    void saveFaculty() {
        given(facultyRepository.save(DEFAULT_FACULTY)).willReturn(DEFAULT_FACULTY);
        given(facultyMapper.toFaculty(DEFAULT_FACULTY_DTO)).willReturn(DEFAULT_FACULTY);
        given(facultyMapper.toFacultyDto(DEFAULT_FACULTY)).willReturn(DEFAULT_FACULTY_DTO);

        assertEquals(DEFAULT_FACULTY_DTO, facultyService.saveFaculty(DEFAULT_FACULTY_DTO));

        then(facultyRepository).should(only()).save(DEFAULT_FACULTY);
        then(facultyMapper).should(times(1)).toFaculty(DEFAULT_FACULTY_DTO);
        then(facultyMapper).should(times(1)).toFacultyDto(DEFAULT_FACULTY);
        then(facultyMapper).shouldHaveNoMoreInteractions();
    }

    @Test
    void getFaculty() {
        List<Faculty> faculty = List.of(DEFAULT_FACULTY);
        given(facultyRepository.findAll()).willReturn(faculty);
        given(facultyMapper.toFacultyDto(DEFAULT_FACULTY)).willReturn(DEFAULT_FACULTY_DTO);

        assertEquals(DEFAULT_FACULTY_DTO, facultyService.getFaculty().get(0));

        then(facultyRepository).should(only()).findAll();
        then(facultyMapper).should(times(1)).toFacultyDto(DEFAULT_FACULTY);
        then(facultyMapper).shouldHaveNoMoreInteractions();

    }

    @Test
    void getFacultyById() {
        given(facultyRepository.findById(DEFAULT_ID)).willReturn(Optional.ofNullable(DEFAULT_FACULTY));
        given(facultyMapper.toFacultyDto(DEFAULT_FACULTY)).willReturn(DEFAULT_FACULTY_DTO);

        assertEquals(DEFAULT_FACULTY_DTO, facultyService.getFacultyById(DEFAULT_ID));

        then(facultyRepository).should(only()).findById(DEFAULT_ID);
        then(facultyMapper).should(times(1)).toFacultyDto(DEFAULT_FACULTY);
        then(facultyMapper).shouldHaveNoMoreInteractions();
    }

    @Test
    void deleteFaculty() {
        facultyService.deleteFaculty(DEFAULT_ID);

        then(facultyRepository).should(only()).deleteById(DEFAULT_ID);
    }

    @Test
    void updateFaculty() {
        given(facultyRepository.save(DEFAULT_FACULTY)).willReturn(DEFAULT_FACULTY);
        given(facultyMapper.toFaculty(DEFAULT_FACULTY_DTO)).willReturn(DEFAULT_FACULTY);
        given(facultyMapper.toFacultyDto(DEFAULT_FACULTY)).willReturn(DEFAULT_FACULTY_DTO);

        assertEquals(DEFAULT_FACULTY_DTO, facultyService.updateFaculty(DEFAULT_ID, DEFAULT_FACULTY_DTO));

        then(facultyRepository).should(only()).save(DEFAULT_FACULTY);
        then(facultyMapper).should(times(1)).toFaculty(DEFAULT_FACULTY_DTO);
        then(facultyMapper).should(times(1)).toFacultyDto(DEFAULT_FACULTY);
        then(facultyMapper).shouldHaveNoMoreInteractions();
    }

    @Test
    void getFacultyByIdNotFoundException() {

        given(facultyRepository.findById(DEFAULT_ID)).willReturn(Optional.empty());

        NotFoundException notFoundException = assertThrows(NotFoundException.class,
                () -> facultyService.getFacultyById(DEFAULT_ID));
        assertEquals(String.format(NOT_FOUND_BY_ID, DEFAULT_ID), notFoundException.getMessage());

        then(facultyRepository).should(only()).findById(DEFAULT_ID);
    }
}