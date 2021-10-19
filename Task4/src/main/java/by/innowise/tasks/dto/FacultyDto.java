package by.innowise.tasks.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FacultyDto {

    private Long id;
    private String name;
    private List<DepartmentDto> departmentsDto;
}
