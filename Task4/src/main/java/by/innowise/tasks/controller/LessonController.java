package by.innowise.tasks.controller;

import by.innowise.tasks.dto.LessonDto;
import by.innowise.tasks.dto.StudyHourDto;
import by.innowise.tasks.service.LessonService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lesson")
@RequiredArgsConstructor
public class LessonController {

    private final LessonService lessonService;

    @GetMapping
    public List<StudyHourDto> getAllLessons() {
        return lessonService.getAllStudyHours();
    }

    @GetMapping("/{id}")
    public StudyHourDto getLessonById(@PathVariable Long id) {
        return lessonService.getStudyHourById(id);
    }

    @PostMapping
    public void saveLesson(@RequestBody LessonDto lessonDto) {
        lessonService.save(lessonDto);
    }

    @PutMapping()
    public void updateLesson(@RequestBody LessonDto lessonDto) {
        lessonService.update(lessonDto);
    }

    @DeleteMapping("/{id}")
    public void deleteLesson(@PathVariable Long id) {
        lessonService.deleteLesson(id);
    }
}
