package by.innowise.tasks.controller;

import by.innowise.tasks.dto.StudyHourDto;
import by.innowise.tasks.service.StudyHourService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/studyHour")
public class StudyHourController {

    @Autowired
    private StudyHourService studyHourService;

    @GetMapping
    public List<StudyHourDto> getStudyHour() {
        return studyHourService.getStudyHours();
    }

    @GetMapping("/{id}")
    public StudyHourDto getStudyHourById(@PathVariable Long id) {
        return studyHourService.getStudyHourById(id);
    }

    @PostMapping
    public void saveStudyHour(@RequestBody StudyHourDto studyHourDto) {
        studyHourService.saveStudyHour(studyHourDto);
    }

    @PutMapping("/{id}")
    public void updateStudyHour(@PathVariable Long id, @RequestBody StudyHourDto studyHourDto) {
        studyHourService.updateStudyHour(id, studyHourDto);
    }

    @DeleteMapping("/{id}")
    public void deleteStudyHour(@PathVariable Long id) {
        studyHourService.deleteStudyHour(id);
    }
}
