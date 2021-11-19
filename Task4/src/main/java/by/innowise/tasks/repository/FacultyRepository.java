package by.innowise.tasks.repository;

import by.innowise.tasks.entity.Faculty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.stream.Stream;

@Repository
public interface FacultyRepository extends JpaRepository<Faculty, Long> {

    @Query("Select f From Faculty f")
    Stream<Faculty> getAll();
}
