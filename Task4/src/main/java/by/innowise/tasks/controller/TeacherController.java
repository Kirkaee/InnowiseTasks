package by.innowise.tasks.controller;

import by.innowise.tasks.dto.TeacherDto;
import by.innowise.tasks.service.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/teacher")
@RequiredArgsConstructor
public class TeacherController {

    private final TeacherService teacherService;

    @GetMapping
    public List<TeacherDto> getAllTeachers() {
        return teacherService.getAllTeachers();
    }

    @GetMapping("/{id}")
    public TeacherDto getTeacherById(@PathVariable Long id) {
        return teacherService.getTeacherById(id);
    }

    @PostMapping
    public void saveTeacher(@RequestBody TeacherDto teacherDto) {
        teacherService.saveTeacher(teacherDto);
    }

    @PutMapping()
    public void updateTeacher(@RequestBody TeacherDto teacherDto) {
        teacherService.updateTeacher(teacherDto);
    }

    @DeleteMapping("/{id}")
    public void deleteTeacher(@PathVariable Long id) {
        teacherService.deleteTeacher(id);
    }
}
