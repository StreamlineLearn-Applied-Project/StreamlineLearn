package com.StreamlineLearn.ContentManagement.service;


import com.StreamlineLearn.ContentManagement.dto.ContentDto;
import com.StreamlineLearn.ContentManagement.model.Content;

import java.util.Optional;
import java.util.Set;

public interface ContentService {
    Content createContent(Long courseId, Content content, String authorizationHeader);

    Set<Content> getContentsByCourseId(Long id);

    Optional<ContentDto> getContentById(Long courseId, Long contentId, String authorizationHeader);

    boolean updateContentById(Long courseId, Long contentId, Content content, String authorizationHeader);

    boolean deleteContentById(Long courseId, Long contentId, String authorizationHeader);

}

