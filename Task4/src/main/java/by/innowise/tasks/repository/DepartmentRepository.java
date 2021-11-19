package by.innowise.tasks.repository;

import by.innowise.tasks.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.stream.Stream;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {

    @Query("Select d From Department d")
    Stream<Department> getAll();

}
