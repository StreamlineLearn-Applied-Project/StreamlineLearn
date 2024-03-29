package com.StreamlineLearn.UserManagement.repository;

import com.StreamlineLearn.UserManagement.model.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InstructorRepository extends JpaRepository<Instructor, Long> {
    Instructor findByUserId(Long userId);
}
