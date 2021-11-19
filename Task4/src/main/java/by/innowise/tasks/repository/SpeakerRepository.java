package by.innowise.tasks.repository;

import by.innowise.tasks.entity.Speaker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.stream.Stream;

@Repository
public interface SpeakerRepository extends JpaRepository<Speaker, Long> {

    @Query("Select s From Speaker s")
    Stream<Speaker> getAll();
}
