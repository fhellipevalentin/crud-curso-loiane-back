package fhellipe.github.com.crudbyloianeback.dto.mapper;
import fhellipe.github.com.crudbyloianeback.dto.CourseDTO;
import fhellipe.github.com.crudbyloianeback.dto.LessonDTO;
import fhellipe.github.com.crudbyloianeback.enums.Category;
import fhellipe.github.com.crudbyloianeback.model.Course;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseMapper {

    // aqui que se convertem os dados recebidos e a própria entidade para o DTO

    public CourseDTO toDTO(Course course) {

        if (course == null) {
            return null;
        }
        List<LessonDTO> lessons = course.getLessons()
                .stream()
                .map(lesson -> new LessonDTO(lesson.getId(), lesson.getName(), lesson.getYoutubeUrl()))
                .collect(Collectors.toList());
        return new CourseDTO(course.getId(), course.getName(), course.getCategory().getValue(), lessons);
    }
    public Course toEntity(CourseDTO courseDTO) {

        if (courseDTO == null) {
            return null;
        }

        Course course = new Course();
        if (courseDTO.id() != null) {
            course.setId(courseDTO.id());
        }
        course.setName(courseDTO.name());
        course.setCategory(convertCategoryValue(courseDTO.category()));
        return course;

        // Builder pattern
    }

    /* aqui é onde a conversão dinâmica de tipos enumerados se dispõe para o DTO */

    public Category convertCategoryValue(String value) {
        if (value == null) {
            return null;
        }
        return switch (value) {
            case "Front-End" -> Category.FRONT_END;
            case "Back-End" -> Category.BACK_END;
            default -> throw new IllegalArgumentException("Categoria inválida: " + value);
        };
    }
}
