package by.innowise.tasks.mapper;

import by.innowise.tasks.dto.DepartmentDto;
import by.innowise.tasks.dto.TeacherDto;
import by.innowise.tasks.entity.Department;
import by.innowise.tasks.entity.Teacher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DepartmentMapper {

    private final TeacherMapper teacherMapper;

    public DepartmentDto toDepartmentDto(Department department) {
        List<TeacherDto> teachers = department.getTeachers().stream()
                .map(teacherMapper::toTeacherDto)
                .toList();
        return DepartmentDto.builder()
                .id(department.getId())
                .name(department.getName())
                .teachersDto(teachers)
                .build();
    }

    public Department toDepartment(DepartmentDto departmentDto) {
        List<Teacher> teachers = departmentDto.getTeachersDto().stream()
                .map(teacherMapper::toTeacher)
                .toList();
        return Department.builder()
                .id(departmentDto.getId())
                .name(departmentDto.getName())
                .teachers(teachers)
                .build();
    }

}
