package com.StreamlineLearn.ContentManagement.repository;

import com.StreamlineLearn.ContentManagement.model.ContentMedia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ContentMediaRepository extends JpaRepository<ContentMedia, Long> {

    Optional<ContentMedia> findByMediaName(String mediaName);

}
