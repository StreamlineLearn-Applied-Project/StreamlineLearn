package com.StreamlineLearn.CourseManagement.repository;

import com.StreamlineLearn.CourseManagement.model.CourseMedia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CourseMediaRepository extends JpaRepository<CourseMedia, Long> {
    Optional<CourseMedia> findByMediaName(String mediaName);
}
