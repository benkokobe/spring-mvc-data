package com.bko.persistence;

import java.util.List;

import com.bko.domain.Course;


public interface CourseDao {
    public void store(Course course);

    public void delete(Long courseId);

    public Course findById(Long courseId);

    public List<Course> findAll();
}
