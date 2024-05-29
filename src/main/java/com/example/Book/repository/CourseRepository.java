package com.example.Book.repository;

import com.example.Book.Model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface CourseRepository extends JpaRepository<Course, Integer> {
    List<Course> findByLectureNameContainingIgnoreCase(String lectureName);
}
