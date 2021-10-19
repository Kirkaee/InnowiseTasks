package by.innowise.tasks.repository;

import by.innowise.tasks.entity.StudyHour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudyHourRepository extends JpaRepository<StudyHour, Long> {

    Optional<StudyHour> findById(Long id);

}
