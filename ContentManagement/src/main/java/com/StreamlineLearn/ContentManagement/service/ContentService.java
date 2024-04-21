package com.StreamlineLearn.ContentManagement.service;


import com.StreamlineLearn.ContentManagement.model.Content;

import java.util.Set;

public interface ContentService {
    void createContent(Long courseId, Content content, String authorizationHeader);

    Content getContentById(Long id);

    boolean updateContentById(Long id, Content content);

    boolean deleteContentById(Long id);

    Set<Content> getContentByCourseId(Long id);
}

