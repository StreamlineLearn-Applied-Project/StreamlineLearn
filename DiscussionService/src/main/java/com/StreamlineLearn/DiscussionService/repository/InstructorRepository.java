package com.StreamlineLearn.DiscussionService.repository;


import com.StreamlineLearn.DiscussionService.model.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InstructorRepository extends JpaRepository<Instructor, Long> {

}
