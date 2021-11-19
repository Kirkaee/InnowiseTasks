package by.innowise.tasks.repository;

import by.innowise.tasks.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.stream.Stream;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {

    @Query("Select s From Subject s")
    Stream<Subject> getAll();
}
