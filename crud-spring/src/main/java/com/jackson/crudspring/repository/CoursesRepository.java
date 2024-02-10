package com.jackson.crudspring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jackson.crudspring.model.Course;

@Repository
public interface CoursesRepository extends JpaRepository<Course, Long>{

}