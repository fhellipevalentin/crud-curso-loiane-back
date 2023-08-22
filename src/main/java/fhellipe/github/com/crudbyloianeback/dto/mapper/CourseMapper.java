package fhellipe.github.com.crudbyloianeback.dto.mapper;
import fhellipe.github.com.crudbyloianeback.dto.CourseDTO;
import fhellipe.github.com.crudbyloianeback.model.Course;
import org.springframework.stereotype.Service;

@Service
public class CourseMapper {

    // aqui que se convertem os dados recebidos e a própria entidade para o DTO

    public CourseDTO toDTO(Course course) {

        if (course == null) {
            return null;
        }

        return new CourseDTO(course.getId(), course.getName(), course.getCategory());
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
        course.setCategory(courseDTO.category());
        course.setStatus("Ativo");
        return course;

        // Builder pattern
    }

}
