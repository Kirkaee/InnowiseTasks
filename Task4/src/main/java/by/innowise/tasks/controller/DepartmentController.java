package by.innowise.tasks.controller;

import by.innowise.tasks.dto.DepartmentDto;
import by.innowise.tasks.service.DepartmentService;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/department")
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentService;

    @GetMapping
    public List<DepartmentDto> getAllDepartments() {
        return departmentService.getAllDepartments();
    }

    @GetMapping("/{id}")
    public DepartmentDto getDepartmentById(@PathVariable Long id) {
        return departmentService.getDepartmentById(id);
    }

    @PostMapping
    public void saveDepartment(@RequestBody DepartmentDto departmentDto) {
        departmentService.saveDepartment(departmentDto);
    }

    @PutMapping()
    public void updateDepartment(@RequestBody DepartmentDto departmentDto) {
        departmentService.updateDepartment(departmentDto);
    }

    @DeleteMapping("/{id}")
    public void deleteDepartment(@PathVariable Long id) {
        departmentService.deleteDepartment(id);
    }
}
