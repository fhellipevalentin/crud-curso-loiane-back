package fhellipe.github.com.crudbyloianeback.service;

import fhellipe.github.com.crudbyloianeback.dto.CourseDTO;
import fhellipe.github.com.crudbyloianeback.dto.mapper.CourseMapper;
import fhellipe.github.com.crudbyloianeback.exception.RecordNotFoundException;
import fhellipe.github.com.crudbyloianeback.repository.CourseRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;
import java.util.stream.Collectors;

@Validated
@Service
public class CourseService {
    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;

    public CourseService(CourseRepository courseRepository, CourseMapper courseMapper) {
        this.courseRepository = courseRepository;
        this.courseMapper = courseMapper;
    }

    public List<CourseDTO> list() {

        return courseRepository.findAll()
                .stream()
                .map(course -> courseMapper.toDTO(course))
                .collect(Collectors.toList());

        /* modo rudimentar - mais trabalhoso
        List<Course> courses = courseRepository.findAll();
        List<CourseDTO> dtos = new ArrayList<>(courses.size());
        for (Course course: courses) {
            CourseDTO dto = new CourseDTO(course.getId(), course.getName(), course.getCategory());
            dtos.add(dto);
        }
        return dtos;*/
    }

    public CourseDTO findById(@PathVariable @NotNull @Positive Long id) {
        return courseRepository.findById(id)
                .map(course -> courseMapper.toDTO(course))
                .orElseThrow(() -> new RecordNotFoundException(id));
    }

    public CourseDTO create(@Valid @NotNull CourseDTO course) {
        // System.out.println(course.getName());
        return courseMapper.toDTO(courseRepository.save(courseMapper.toEntity(course)));
        // return ResponseEntity.status(HttpStatus.CREATED).body(courseRepository.save(course));
    }

    public CourseDTO update(@Valid @NotNull @Positive Long id, CourseDTO course) {
        return courseRepository.findById(id)
                .map(recordFound -> {
                    recordFound.setName(course.name());
                    recordFound.setCategory(course.category());
                    return courseMapper.toDTO(courseRepository.save(recordFound));
                })
                .orElseThrow(() -> new RecordNotFoundException(id));
    }

    public void delete(@NotNull @Positive @PathVariable Long id) {

        courseRepository.delete(courseRepository.findById(id).orElseThrow(() -> new RecordNotFoundException(id)));
        /*courseRepository.findById(id)
                .map(recordFound -> {
                    courseRepository.deleteById(id);
                    return true;
                }).orElseThrow(() -> new RecordNotFoundException(id));*/
    }
}
