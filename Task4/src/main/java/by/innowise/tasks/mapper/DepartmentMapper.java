package by.innowise.tasks.mapper;

import by.innowise.tasks.dto.DepartmentDto;
import by.innowise.tasks.entity.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DepartmentMapper {

    @Autowired
    private TeacherMapper teacherMapper;

    public DepartmentDto toDepartmentDto(Department department) {
        return DepartmentDto.builder()
                .id(department.getId())
                .name(department.getName())
                .teachersDto(department.getTeachers().stream()
                        .map(teacher -> teacherMapper.toTeacherDto(teacher))
                        .toList())
                .build();
    }

    public Department toDepartment(DepartmentDto departmentDto) {
        return Department.builder()
                .id(departmentDto.getId())
                .name(departmentDto.getName())
                .teachers(departmentDto.getTeachersDto().stream()
                        .map(teacher -> teacherMapper.toTeacher(teacher))
                        .toList())
                .build();
    }

}
