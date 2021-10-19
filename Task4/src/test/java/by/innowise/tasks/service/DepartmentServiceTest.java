package by.innowise.tasks.service;

import by.innowise.tasks.dto.DepartmentDto;
import by.innowise.tasks.entity.Department;
import by.innowise.tasks.handler.NotFoundException;
import by.innowise.tasks.mapper.DepartmentMapper;
import by.innowise.tasks.repository.DepartmentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static by.innowise.tasks.service.DepartmentService.NOT_FOUND_BY_ID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.only;


@ExtendWith(MockitoExtension.class)
class DepartmentServiceTest {

    public static final Long DEFAULT_ID = 0L;
    public static final String DEFAULT_NAME = "name";

    public static final Department DEFAULT_DEPARTMENT = Department.builder()
            .id(DEFAULT_ID)
            .name(DEFAULT_NAME)
            .build();

    public static final DepartmentDto DEFAULT_DEPARTMENT_DTO = DepartmentDto.builder()
            .id(DEFAULT_ID)
            .name(DEFAULT_NAME)
            .build();

    @Mock
    private DepartmentRepository departmentRepository;

    @Mock
    private DepartmentMapper departmentMapper;

    @InjectMocks
    private DepartmentService departmentService;

    @Test
    void saveDepartment() {
        given(departmentRepository.save(DEFAULT_DEPARTMENT)).willReturn(DEFAULT_DEPARTMENT);
        given(departmentMapper.toDepartment(DEFAULT_DEPARTMENT_DTO)).willReturn(DEFAULT_DEPARTMENT);
        given(departmentMapper.toDepartmentDto(DEFAULT_DEPARTMENT)).willReturn(DEFAULT_DEPARTMENT_DTO);

        assertEquals(DEFAULT_DEPARTMENT_DTO, departmentService.saveDepartment(DEFAULT_DEPARTMENT_DTO));

        then(departmentRepository).should(only()).save(DEFAULT_DEPARTMENT);
        then(departmentMapper).should(Mockito.times(1)).toDepartment(DEFAULT_DEPARTMENT_DTO);
        then(departmentMapper).should(Mockito.times(1)).toDepartmentDto(DEFAULT_DEPARTMENT);
        then(departmentMapper).shouldHaveNoMoreInteractions();
    }

    @Test
    void getDepartments() {
        List<Department> departments = List.of(DEFAULT_DEPARTMENT);
        given(departmentRepository.findAll()).willReturn(departments);
        given(departmentMapper.toDepartmentDto(DEFAULT_DEPARTMENT)).willReturn(DEFAULT_DEPARTMENT_DTO);

        assertEquals(DEFAULT_DEPARTMENT_DTO, departmentService.getAllDepartments().get(0));

        then(departmentRepository).should(only()).findAll();
        then(departmentMapper).should(Mockito.times(1)).toDepartmentDto(DEFAULT_DEPARTMENT);
        then(departmentMapper).shouldHaveNoMoreInteractions();
    }

    @Test
    void getDepartmentById() {
        given(departmentRepository.findById(DEFAULT_ID)).willReturn(Optional.ofNullable(DEFAULT_DEPARTMENT));
        given(departmentMapper.toDepartmentDto(DEFAULT_DEPARTMENT)).willReturn(DEFAULT_DEPARTMENT_DTO);

        assertEquals(DEFAULT_DEPARTMENT_DTO, departmentService.getDepartmentById(DEFAULT_ID));

        then(departmentRepository).should(only()).findById(DEFAULT_ID);
        then(departmentMapper).should(Mockito.times(1)).toDepartmentDto(DEFAULT_DEPARTMENT);
        then(departmentMapper).shouldHaveNoMoreInteractions();
    }

    @Test
    void deleteDepartment() {
        departmentService.deleteDepartment(DEFAULT_ID);

        then(departmentRepository).should(only()).deleteById(DEFAULT_ID);
    }

    @Test
    void updateDepartment() {

        given(departmentRepository.save(DEFAULT_DEPARTMENT)).willReturn(DEFAULT_DEPARTMENT);
        given(departmentMapper.toDepartment(DEFAULT_DEPARTMENT_DTO)).willReturn(DEFAULT_DEPARTMENT);
        given(departmentMapper.toDepartmentDto(DEFAULT_DEPARTMENT)).willReturn(DEFAULT_DEPARTMENT_DTO);

        assertEquals(DEFAULT_DEPARTMENT_DTO, departmentService.updateDepartment(DEFAULT_ID, DEFAULT_DEPARTMENT_DTO));

        then(departmentRepository).should(only()).save(DEFAULT_DEPARTMENT);
        then(departmentMapper).should(Mockito.times(1)).toDepartment(DEFAULT_DEPARTMENT_DTO);
        then(departmentMapper).should(Mockito.times(1)).toDepartmentDto(DEFAULT_DEPARTMENT);
        then(departmentMapper).shouldHaveNoMoreInteractions();
    }

    @Test
    void getDepartmentByIdNotFoundException() {

        given(departmentRepository.findById(DEFAULT_ID)).willReturn(Optional.empty());

        NotFoundException notFoundException = assertThrows(NotFoundException.class,
                () -> departmentService.getDepartmentById(DEFAULT_ID));
        assertEquals(String.format(NOT_FOUND_BY_ID, DEFAULT_ID), notFoundException.getMessage());

        then(departmentRepository).should(only()).findById(DEFAULT_ID);
    }
}