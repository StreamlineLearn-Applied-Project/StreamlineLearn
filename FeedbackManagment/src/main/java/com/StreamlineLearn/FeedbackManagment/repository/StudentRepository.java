package com.StreamlineLearn.FeedbackManagment.repository;


import com.StreamlineLearn.FeedbackManagment.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
