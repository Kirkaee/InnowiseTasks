package by.innowise.tasks.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "faculty")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Faculty {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "faculty_id")
    private Long id;

    @Column(name = "faculty_name")
    private String name;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(mappedBy = "faculty", cascade = CascadeType.ALL)
    private List<Department> departments;

}
