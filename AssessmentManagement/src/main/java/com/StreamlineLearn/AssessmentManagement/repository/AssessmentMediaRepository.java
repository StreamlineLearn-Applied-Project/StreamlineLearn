package com.StreamlineLearn.AssessmentManagement.repository;

import com.StreamlineLearn.AssessmentManagement.model.AssessmentMedia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AssessmentMediaRepository extends JpaRepository<AssessmentMedia, Long> {

    Optional<AssessmentMedia> findByMediaName(String mediaName);

}
