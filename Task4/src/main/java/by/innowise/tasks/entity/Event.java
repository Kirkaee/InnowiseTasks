package by.innowise.tasks.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.List;

@Entity
@DiscriminatorValue("event")
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Event extends StudyHour {

    @Column(name = "event_topic")
    private String topic;

    @ManyToMany
    @JoinTable(name = "event_speakers",
            joinColumns = @JoinColumn(name = "study_hour_id"),
            inverseJoinColumns = @JoinColumn(name = "speaker_id"))
    private List<Speaker> speakers;
}
