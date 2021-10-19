package by.innowise.tasks.controller;

import by.innowise.tasks.dto.FacultyDto;
import by.innowise.tasks.service.FacultyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/faculty")
public class FacultyController {

    @Autowired
    private FacultyService facultyService;

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

    @PutMapping("/{id}")
    public void updateFaculty(@PathVariable Long id, @RequestBody FacultyDto facultyDto) {
        facultyService.updateFaculty(id, facultyDto);
    }

    @DeleteMapping("/{id}")
    public void deleteFaculty(@PathVariable Long id) {
        facultyService.deleteFaculty(id);
    }
}
