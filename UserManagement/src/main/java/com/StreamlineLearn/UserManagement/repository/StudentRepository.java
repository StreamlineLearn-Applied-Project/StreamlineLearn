package com.StreamlineLearn.UserManagement.repository;

import com.StreamlineLearn.UserManagement.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {

    Student findByUserId(Long user_id);
}
