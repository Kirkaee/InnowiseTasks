package by.innowise.tasks.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "room")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_id")
    private Long id;

    @Column(name = "room_type")
    private String type;

    @Column(name = "room_capacity")
    private Integer capacity;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    private List<StudyHour> studyHours;

}
