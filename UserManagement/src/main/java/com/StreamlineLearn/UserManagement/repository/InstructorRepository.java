package com.StreamlineLearn.UserManagement.repository;

import com.StreamlineLearn.UserManagement.model.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InstructorRepository extends JpaRepository<Instructor, Long> {
    Optional<Instructor> findByUserId(Long userId);
}
