package com.jackson.crudspring.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.jackson.crudspring.dto.CourseDTO;
import com.jackson.crudspring.dto.mapper.CourseMapper;
import com.jackson.crudspring.exepeption.RecordNotFoundException;
import com.jackson.crudspring.model.Course;
import com.jackson.crudspring.repository.CoursesRepository;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Validated
@Service
public class CourseService {
    
    private CoursesRepository coursesRepository;
    private CourseMapper courseMapper;

    public CourseService(CoursesRepository coursesRepository, CourseMapper courseMapper) {
        super();
        this.coursesRepository = coursesRepository;
        this.courseMapper = courseMapper;
    }

    public List<CourseDTO> list(){
        return coursesRepository.findAll()
            .stream()
            .map(courseMapper::toDTO)
            .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }

    public CourseDTO findById(@NotNull @Positive Long id){
        return coursesRepository.findById(id)
                .map(courseMapper::toDTO)
                .orElseThrow(() -> new RecordNotFoundException(id));
    }

    public CourseDTO create(@Valid @NotNull CourseDTO course){
        return courseMapper.toDTO(coursesRepository.save(courseMapper.toEntity(course)));
    }

    public CourseDTO update(@NotNull @Positive Long id, @Valid CourseDTO courseDTO){
        return coursesRepository.findById(id)
                .map(x -> {
                    Course course = courseMapper.toEntity(courseDTO);
                    x.setName(courseDTO.name());
                    x.setCategory(courseMapper.convertCategory(courseDTO.category()));
                    x.getLessons().clear();
                    course.getLessons().forEach(x.getLessons()::add);
                    return courseMapper.toDTO(coursesRepository.save(x));
                }).orElseThrow(() -> new RecordNotFoundException(id));
    }

    public void delete(@NotNull @Positive Long id){
        coursesRepository.delete(coursesRepository.findById(id).orElseThrow(() -> new RecordNotFoundException(id)));
    }

}
