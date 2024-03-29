package fhellipe.github.com.crudbyloianeback.controller;

import fhellipe.github.com.crudbyloianeback.dto.CourseDTO;
import fhellipe.github.com.crudbyloianeback.dto.mapper.CourseMapper;
import fhellipe.github.com.crudbyloianeback.enums.Category;
import fhellipe.github.com.crudbyloianeback.model.Course;
import fhellipe.github.com.crudbyloianeback.model.Lesson;
import fhellipe.github.com.crudbyloianeback.repository.CourseRepository;
import fhellipe.github.com.crudbyloianeback.service.CourseService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@CrossOrigin("http://localhost:4200")
@Validated
@RestController
@RequestMapping("/api/courses")
public class CourseController {
    private final CourseService courseService;


    public CourseController(CourseService courseService, CourseMapper courseMapper) {
        this.courseService = courseService;
    }

    @GetMapping
    public List<CourseDTO> list() {
        return courseService.list();
    }

    @PostMapping()
    @ResponseStatus(code = HttpStatus.CREATED)
    public CourseDTO create(@RequestBody @Valid @NotNull CourseDTO course) {
        // System.out.println(course.getName());
        return  courseService.create(course);
        // return ResponseEntity.status(HttpStatus.CREATED).body(courseRepository.save(course));
    }

    @GetMapping("/{id}")
    public CourseDTO findById(@PathVariable @NotNull @Positive Long id) {
        return courseService.findById(id);
    }

    @PutMapping("/{id}")
    public CourseDTO update(@NotNull @Positive @PathVariable Long id, @RequestBody @Valid @NotNull CourseDTO course) {
        return courseService.update(id, course);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void delete(@NotNull @Positive @PathVariable Long id) {
        courseService.delete(id);
    }

    @Bean
    CommandLineRunner initDataBase(CourseRepository courseRepository) {
        return args -> {
            courseRepository.deleteAll();
            Course c = new Course();
            c.setName("Angular com Spring");
            c.setCategory(Category.FRONT_END);

            Lesson l = new Lesson();
            l.setName("Introdução");
            l.setYoutubeUrl("watch?v=1");
            l.setCourse(c);
            c.getLessons().add(l);

            Lesson l2 = new Lesson();
            l2.setName("Angular");
            l2.setYoutubeUrl("watch?v=2");
            l2.setCourse(c);
            c.getLessons().add(l2);

            courseRepository.save(c);
        };
    }
}
