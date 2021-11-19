package by.innowise.tasks.repository;

import by.innowise.tasks.entity.StudyHour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.stream.Stream;

@Repository
public interface StudyHourRepository extends JpaRepository<StudyHour, Long> {

    @Query("Select s From StudyHour s")
    Stream<StudyHour> getAll();

}
