package com.jackson.crudspring.dto.mapper;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import java.util.List;
import org.springframework.stereotype.Component;

import com.jackson.crudspring.dto.CourseDTO;
import com.jackson.crudspring.dto.LessonDTO;
import com.jackson.crudspring.enums.Category;
import com.jackson.crudspring.model.Course;
import com.jackson.crudspring.model.Lesson;

@Component
public class CourseMapper {
    
    public CourseDTO toDTO(Course course){
        if(course == null){
            return null;
        }
        
        List<LessonDTO> lessons = course.getLessons()
        .stream()
        .map(lesson -> new LessonDTO(lesson.getId(), 
                                     lesson.getName(), 
                                     lesson.getYoutubeUrl()))
        .collect(Collectors.toList());

        return new CourseDTO(course.getId(), 
                            course.getName(), 
                            course.getCategory().getValue(), 
                            lessons);
    }

    public Course toEntity(CourseDTO courseDTO){
        if(courseDTO == null){
            return null;
        }

        Course course = new Course();
        if(courseDTO.id() != null){
            course.setId(courseDTO.id());
        }
        course.setName(courseDTO.name());
        course.setCategory(this.convertCategory(courseDTO.category()));

        List<Lesson> lessons = courseDTO.lessons().stream().map(lessonDTO -> {
            var lesson = new Lesson();
            lesson.setId(lessonDTO.id());
            lesson.setName(lessonDTO.name());
            lesson.setYoutubeUrl(lessonDTO.youtubeUrl());
            lesson.setCourse(course);
            return lesson;
        }).collect(Collectors.toList());
        course.setLessons(lessons);

        return course;
    }

    public Category convertCategory(String value) {
        if(value == null){
            return null;
        }
        return Stream.of(Category.values())
        .filter(c -> c.getValue().equals(value))
        .findFirst()
        .orElseThrow(IllegalArgumentException::new);
    }
}
