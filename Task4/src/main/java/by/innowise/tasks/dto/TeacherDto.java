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
public class TeacherDto {

    private Long id;
    private String fio;
    private String degree;
    private Integer experience;
    private List<LessonDto> lessonsDto;
}
