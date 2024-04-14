package com.StreamlineLearn.UserManagement.repository;

import com.StreamlineLearn.UserManagement.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findByUserId(Long user_id);
}
