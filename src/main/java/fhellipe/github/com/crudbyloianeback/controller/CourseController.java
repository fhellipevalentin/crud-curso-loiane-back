package fhellipe.github.com.crudbyloianeback.controller;

import fhellipe.github.com.crudbyloianeback.model.Course;
import fhellipe.github.com.crudbyloianeback.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    private final CourseRepository courseRepository;

    public CourseController(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @GetMapping
    public List<Course> list() {
        return courseRepository.findAll();
    }

    @PostMapping()
    @ResponseStatus(code = HttpStatus.CREATED)
    public Course create(@RequestBody Course course) {
        // System.out.println(course.getName());
        return courseRepository.save(course);
        // return ResponseEntity.status(HttpStatus.CREATED).body(courseRepository.save(course));
    }

    @Bean
    CommandLineRunner initDataBase( CourseRepository courseRepository ) {
        return args -> {
            courseRepository.deleteAll();
            Course c = new Course();
            c.setName("Angular com Spring");
            c.setCategory("front-end");
            courseRepository.save(c);
        };
    }

    @GetMapping("/{id}")
    public ResponseEntity<Course> findById(@PathVariable Long id) {
        return courseRepository.findById(id).map(recordFound -> ResponseEntity.ok(recordFound)).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Course> update(@PathVariable Long id, @RequestBody Course course) {
        return courseRepository.findById(id)
                .map(recordFound -> {
                    recordFound.setName(course.getName());
                    recordFound.setCategory(course.getCategory());
                    Course updated = courseRepository.save(recordFound);
                    return ResponseEntity.ok(updated);
                }).orElse(ResponseEntity.notFound().build());
    }


}
