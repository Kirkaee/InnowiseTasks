package by.innowise.tasks.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "typeClass")
@JsonSubTypes({
        @JsonSubTypes.Type(value = EventDto.class, name = "event"),
        @JsonSubTypes.Type(value = LessonDto.class, name = "lesson")
})
public abstract class StudyHourDto {

    private Long id;
    private Timestamp classDate;
    private String type;


}
