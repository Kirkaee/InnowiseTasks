package by.innowise.tasks.controller;

import by.innowise.tasks.dto.FacultyDto;
import by.innowise.tasks.service.FacultyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/faculty")
@RequiredArgsConstructor
public class FacultyController {

    private final FacultyService facultyService;

    @GetMapping
    public List<FacultyDto> getAllFaculties() {
        return facultyService.getAllFaculties();
    }

    @GetMapping("/{id}")
    public FacultyDto getFacultyById(@PathVariable Long id) {
        return facultyService.getFacultyById(id);
    }

    @PostMapping
    public void saveFaculty(@RequestBody FacultyDto facultyDto) {
        facultyService.saveFaculty(facultyDto);
    }

    @PutMapping()
    public void updateFaculty(@RequestBody FacultyDto facultyDto) {
        facultyService.updateFaculty(facultyDto);
    }

    @DeleteMapping("/{id}")
    public void deleteFaculty(@PathVariable Long id) {
        facultyService.deleteFaculty(id);
    }
}
