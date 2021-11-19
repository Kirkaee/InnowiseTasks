package by.innowise.tasks.repository;

import by.innowise.tasks.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.stream.Stream;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {

    @Query("Select s From Teacher s")
    Stream<Teacher> getAll();
}
