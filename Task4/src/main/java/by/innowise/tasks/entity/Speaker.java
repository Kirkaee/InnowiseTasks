package by.innowise.tasks.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "speaker")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
@ToString
public class Speaker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "speaker_id")
    private Long id;

    @Column(name = "speaker_fio")
    private String fio;

    @Column(name = "speaker_title")
    private String title;

    @Column(name = "speaker_membership")
    private String membership;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToMany(mappedBy = "speakers", cascade = CascadeType.ALL)
    private List<Event> events;

}
