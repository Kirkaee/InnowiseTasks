package by.innowise.tasks.controller;

import by.innowise.tasks.dto.SubjectDto;
import by.innowise.tasks.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/subject")
public class SubjectController {

    @Autowired
    private SubjectService subjectService;

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

    @PutMapping("/{id}")
    public void updateSubject(@PathVariable Long id, @RequestBody SubjectDto subjectDto) {
        subjectService.updateSubject(id, subjectDto);
    }

    @DeleteMapping("/{id}")
    public void deleteSubject(@PathVariable Long id) {
        subjectService.deleteSubject(id);
    }

}
