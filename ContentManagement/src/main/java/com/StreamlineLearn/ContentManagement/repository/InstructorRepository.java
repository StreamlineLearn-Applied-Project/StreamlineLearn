package com.StreamlineLearn.ContentManagement.repository;

import com.StreamlineLearn.ContentManagement.model.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InstructorRepository extends JpaRepository<Instructor, Long> {

}
