package by.innowise.tasks.mapper;

import by.innowise.tasks.dto.FacultyDto;
import by.innowise.tasks.entity.Faculty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FacultyMapper {

    @Autowired
    private DepartmentMapper departmentMapper;

    public Faculty toFaculty(FacultyDto facultyDto) {
        return Faculty.builder()
                .id(facultyDto.getId())
                .name(facultyDto.getName())
                .departments(facultyDto.getDepartmentsDto().stream()
                        .map(departmentDto -> departmentMapper.toDepartment(departmentDto))
                        .toList())
                .build();
    }

    public FacultyDto toFacultyDto(Faculty faculty) {
        return FacultyDto.builder()
                .id(faculty.getId())
                .name(faculty.getName())
                .departmentsDto(faculty.getDepartments().stream()
                        .map(department -> departmentMapper.toDepartmentDto(department))
                        .toList())
                .build();
    }
}
