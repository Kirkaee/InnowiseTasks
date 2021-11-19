package by.innowise.tasks.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SpeakerDto {

    private Long id;
    private String fio;
    private String title;
    private String membership;
}
