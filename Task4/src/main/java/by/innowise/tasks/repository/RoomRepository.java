package by.innowise.tasks.repository;

import by.innowise.tasks.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.stream.Stream;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

    @Query("Select r From Room r")
    Stream<Room> getAll();
}
