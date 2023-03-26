package fhellipe.github.com.crudbyloianeback.controller;

import fhellipe.github.com.crudbyloianeback.model.Course;
import fhellipe.github.com.crudbyloianeback.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


}
