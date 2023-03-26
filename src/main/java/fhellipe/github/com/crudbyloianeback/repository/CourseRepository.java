package fhellipe.github.com.crudbyloianeback.repository;

import fhellipe.github.com.crudbyloianeback.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
}
