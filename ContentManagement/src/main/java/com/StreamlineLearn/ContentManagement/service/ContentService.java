package com.StreamlineLearn.ContentManagement.service;


import com.StreamlineLearn.ContentManagement.model.Content;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.Set;

public interface ContentService {
    Content createContent(Long courseId, Content content, MultipartFile file, String authorizationHeader);

    Set<Content> getContentsByCourseId(Long courseId,String authorizationHeader);

    Optional<Content> getContentById(Long courseId, Long contentId, String authorizationHeader);

    InputStream getContentMedia(Long courseId, String fileName, String authorizationHeader) throws IOException;

    boolean updateContentById(Long courseId, Long contentId, Content content, MultipartFile file, String authorizationHeader);

    boolean deleteContentById(Long courseId, Long contentId, String authorizationHeader);


}

