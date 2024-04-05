package com.StreamlineLearn.DiscussionService.repository;

import com.StreamlineLearn.DiscussionService.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
