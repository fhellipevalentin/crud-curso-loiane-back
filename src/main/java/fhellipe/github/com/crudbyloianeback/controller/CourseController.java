package fhellipe.github.com.crudbyloianeback.controller;

import fhellipe.github.com.crudbyloianeback.model.Course;
import fhellipe.github.com.crudbyloianeback.repository.CourseRepository;
import fhellipe.github.com.crudbyloianeback.service.CourseService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("/api/courses")
public class CourseController {
    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    public List<Course> list() {
        return courseService.list();
    }

    @PostMapping()
    @ResponseStatus(code = HttpStatus.CREATED)
    public Course create(@RequestBody @Valid Course course) {
        // System.out.println(course.getName());
        return courseService.create(course);
        // return ResponseEntity.status(HttpStatus.CREATED).body(courseRepository.save(course));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Course> findById(@PathVariable @NotNull @Positive Long id) {
        return courseService.findById(id).map(recordFound -> ResponseEntity.ok().body(recordFound)).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Course> update(@Valid @NotNull @Positive @PathVariable Long id, @RequestBody Course course) {
        return courseService.update(id, course)
                .map(recordFound -> {
                    return ResponseEntity.ok().body(recordFound);
                }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@NotNull @Positive @PathVariable Long id) {
        if (courseService.delete(id)) {
            return ResponseEntity.noContent().<Void>build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Bean
    CommandLineRunner initDataBase(CourseRepository courseRepository) {
        return args -> {
            courseRepository.deleteAll();
            Course c = new Course();
            c.setName("Angular com Spring");
            c.setCategory("Front-End");
            courseRepository.save(c);
        };
    }
}
