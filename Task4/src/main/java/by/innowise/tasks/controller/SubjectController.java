package by.innowise.tasks.controller;

import by.innowise.tasks.dto.SubjectDto;
import by.innowise.tasks.service.SubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/subject")
@RequiredArgsConstructor
public class SubjectController {

    private final SubjectService subjectService;

    @GetMapping
    public List<SubjectDto> getAllSubjects() {
        return subjectService.getAllSubjects();
    }

    @GetMapping("/{id}")
    public SubjectDto getSubjectById(@PathVariable Long id) {
        return subjectService.getSubjectById(id);
    }

    @PostMapping
    public void saveSubject(@RequestBody SubjectDto subjectDto) {
        subjectService.saveSubject(subjectDto);
    }

    @PutMapping()
    public void updateSubject(@RequestBody SubjectDto subjectDto) {
        subjectService.updateSubject(subjectDto);
    }

    @DeleteMapping("/{id}")
    public void deleteSubject(@PathVariable Long id) {
        subjectService.deleteSubject(id);
    }

}
