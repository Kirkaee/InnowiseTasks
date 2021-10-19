package by.innowise.tasks.controller;

import by.innowise.tasks.dto.TeacherDto;
import by.innowise.tasks.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/teacher")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @GetMapping
    public List<TeacherDto> getTeachers() {
        return teacherService.getTeachers();
    }

    @GetMapping("/{id}")
    public TeacherDto getTeacherById(@PathVariable Long id) {
        return teacherService.getTeacherById(id);
    }

    @PostMapping
    public void saveTeacher(@RequestBody TeacherDto teacherDto) {
        teacherService.saveTeacher(teacherDto);
    }

    @PutMapping("/{id}")
    public void updateTeacher(@PathVariable Long id, @RequestBody TeacherDto teacherDto) {
        teacherService.updateTeacher(id, teacherDto);
    }

    @DeleteMapping("/{id}")
    public void deleteTeacher(@PathVariable Long id) {
        teacherService.deleteTeacher(id);
    }
}
