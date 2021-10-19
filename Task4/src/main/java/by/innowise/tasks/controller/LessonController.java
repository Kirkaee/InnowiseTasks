package by.innowise.tasks.controller;

import by.innowise.tasks.dto.LessonDto;
import by.innowise.tasks.service.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lesson")
public class LessonController {

    @Autowired
    private LessonService lessonService;

    @GetMapping
    public List<LessonDto> getAllLessons() {
        return lessonService.getAllLessons();
    }

    @GetMapping("/{id}")
    public LessonDto getLessonById(@PathVariable Long id) {
        return lessonService.getLessonById(id);
    }

    @PostMapping
    public void saveLesson(@RequestBody LessonDto lessonDto) {
        lessonService.saveLesson(lessonDto);
    }

    @PutMapping("/{id}")
    public void updateLesson(@PathVariable Long id, @RequestBody LessonDto lessonDto) {
        lessonService.updateLesson(id, lessonDto);
    }

    @DeleteMapping("/{id}")
    public void deleteLesson(@PathVariable Long id) {
        lessonService.deleteLesson(id);
    }
}
