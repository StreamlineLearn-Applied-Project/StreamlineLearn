package com.StreamlineLearn.Notification.repository;



import com.StreamlineLearn.Notification.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
