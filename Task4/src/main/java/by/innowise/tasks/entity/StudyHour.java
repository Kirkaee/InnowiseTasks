package by.innowise.tasks.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "study_hour")
@Data
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "study_hour_type", discriminatorType = DiscriminatorType.STRING)
public abstract class StudyHour {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "study_hour_id")
    private Long id;

    @Column(name = "study_hour_class_date")
    private Timestamp classDate;

    @Column(name = "study_hour_type", insertable = false, updatable = false)
    private String type;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

}
