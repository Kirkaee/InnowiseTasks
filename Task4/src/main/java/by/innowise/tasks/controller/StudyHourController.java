package by.innowise.tasks.controller;

import by.innowise.tasks.dto.StudyHourDto;
import by.innowise.tasks.service.StudyHourService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/studyHour")
@RequiredArgsConstructor
public class StudyHourController {

    private final StudyHourService studyHourService;

    @GetMapping
    public List<StudyHourDto> getAllStudyHours() {
        return studyHourService.getAllStudyHours();
    }

    @GetMapping("/{id}")
    public StudyHourDto getStudyHourById(@PathVariable Long id) {
        return studyHourService.getStudyHourById(id);
    }

    @PostMapping
    public void saveStudyHour(@RequestBody StudyHourDto studyHourDto) {
        studyHourService.save(studyHourDto);
    }

    @PutMapping()
    public void updateStudyHour(@RequestBody StudyHourDto studyHourDto) {
        studyHourService.update(studyHourDto);
    }

    @DeleteMapping("/{id}")
    public void deleteStudyHour(@PathVariable Long id) {
        studyHourService.deleteStudyHour(id);
    }
}
