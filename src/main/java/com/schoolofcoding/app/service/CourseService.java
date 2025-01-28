package com.schoolofcoding.app.service;

import com.schoolofcoding.app.model.Course;
import com.schoolofcoding.app.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {
    @Autowired
    CourseRepository courseRepository;

    public Course saveCourse(Course course)
    {
        return courseRepository.save(course);
    }
    public List<Course> getAllCourse()
    {
        return courseRepository.findAll();
    }

}
