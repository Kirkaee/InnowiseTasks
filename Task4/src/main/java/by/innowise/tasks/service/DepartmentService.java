package by.innowise.tasks.service;

import by.innowise.tasks.dto.DepartmentDto;
import by.innowise.tasks.handler.NotFoundException;
import by.innowise.tasks.mapper.DepartmentMapper;
import by.innowise.tasks.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {

    public static final String NOT_FOUND_BY_ID = "There is no such department with id: %s";

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private DepartmentMapper departmentMapper;

    public DepartmentDto saveDepartment(DepartmentDto departmentDto) {
        return departmentMapper
                .toDepartmentDto(departmentRepository
                        .save(departmentMapper
                                .toDepartment(departmentDto)));
    }

    public List<DepartmentDto> getAllDepartments() {
        return departmentRepository
                .findAll()
                .stream()
                .map(departmentMapper::toDepartmentDto)
                .toList();
    }

    public DepartmentDto getDepartmentById(Long id) {
        return departmentMapper
                .toDepartmentDto(departmentRepository
                        .findById(id)
                        .orElseThrow(() -> new NotFoundException(String.format(NOT_FOUND_BY_ID, id))));
    }

    public void deleteDepartment(Long id) {
        departmentRepository
                .deleteById(id);
    }

    public DepartmentDto updateDepartment(Long id, DepartmentDto departmentDto) {
        departmentDto.setId(id);
        return departmentMapper
                .toDepartmentDto(departmentRepository
                        .save(departmentMapper
                                .toDepartment(departmentDto)));
    }
}
