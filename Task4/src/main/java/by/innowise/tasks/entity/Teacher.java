package by.innowise.tasks.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "teacher")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "teacher_id")
    private Long id;

    @Column(name = "teacher_fio")
    private String fio;

    @Column(name = "teacher_degree")
    private String degree;

    @Column(name = "teacher_experience")
    private Integer experience;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL)
    private List<Lesson> lessons;

}
