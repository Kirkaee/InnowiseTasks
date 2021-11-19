package by.innowise.tasks.service;

import by.innowise.tasks.dto.DepartmentDto;
import by.innowise.tasks.handler.NotFoundException;
import by.innowise.tasks.mapper.DepartmentMapper;
import by.innowise.tasks.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentService {

    public static final String NOT_FOUND_BY_ID = "There is no such department with id: %s";

    private final DepartmentRepository departmentRepository;
    private final DepartmentMapper departmentMapper;

    public DepartmentDto saveDepartment(DepartmentDto departmentDto) {
        return departmentMapper
                .toDepartmentDto(departmentRepository
                        .saveAndFlush(departmentMapper
                                .toDepartment(departmentDto)));
    }

    @Transactional(readOnly = true)
    public List<DepartmentDto> getAllDepartments() {
        return departmentRepository.getAll()
                .map(departmentMapper::toDepartmentDto)
                .toList();
    }

    public DepartmentDto getDepartmentById(Long id) {
        return departmentRepository.findById(id)
                .map(departmentMapper::toDepartmentDto)
                .orElseThrow(() -> new NotFoundException(String.format(NOT_FOUND_BY_ID, id)));
    }

    public void deleteDepartment(Long id) {
        departmentRepository.deleteById(id);
    }

    public void updateDepartment(DepartmentDto departmentDto) {
        departmentRepository.save(departmentMapper.toDepartment(departmentDto));
    }
}
