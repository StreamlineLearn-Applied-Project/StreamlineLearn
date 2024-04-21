package com.StreamlineLearn.ContentManagement.repository;

import com.StreamlineLearn.ContentManagement.model.Content;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContentRepository extends JpaRepository<Content, Long> {
}
