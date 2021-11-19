package by.innowise.tasks.mapper;

import by.innowise.tasks.dto.DepartmentDto;
import by.innowise.tasks.dto.FacultyDto;
import by.innowise.tasks.entity.Department;
import by.innowise.tasks.entity.Faculty;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class FacultyMapper {

    private final DepartmentMapper departmentMapper;

    public Faculty toFaculty(FacultyDto facultyDto) {
        List<Department> departments = facultyDto.getDepartmentsDto().stream()
                .map(departmentMapper::toDepartment)
                .toList();
        return Faculty.builder()
                .id(facultyDto.getId())
                .name(facultyDto.getName())
                .departments(departments)
                .build();
    }

    public FacultyDto toFacultyDto(Faculty faculty) {
        List<DepartmentDto> departments = faculty.getDepartments().stream()
                .map(departmentMapper::toDepartmentDto)
                .toList();
        return FacultyDto.builder()
                .id(faculty.getId())
                .name(faculty.getName())
                .departmentsDto(departments)
                .build();
    }
}
