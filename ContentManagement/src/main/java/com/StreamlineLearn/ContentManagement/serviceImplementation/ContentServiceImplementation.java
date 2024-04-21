package com.StreamlineLearn.ContentManagement.serviceImplementation;

import com.StreamlineLearn.ContentManagement.jwtUtil.JwtService;
import com.StreamlineLearn.ContentManagement.model.Content;
import com.StreamlineLearn.ContentManagement.model.Course;
import com.StreamlineLearn.ContentManagement.repository.ContentRepository;
import com.StreamlineLearn.ContentManagement.repository.CourseRepository;
import com.StreamlineLearn.ContentManagement.service.ContentService;
import com.StreamlineLearn.ContentManagement.service.CourseService;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class ContentServiceImplementation implements ContentService {
    private final JwtService jwtService;
    private final CourseService courseService;
    private final ContentRepository contentRepository;
    private final CourseRepository courseRepository;

    public ContentServiceImplementation(JwtService jwtService,
                                        CourseService courseService,
                                        ContentRepository contentRepository, CourseRepository courseRepository) {

        this.jwtService = jwtService;
        this.courseService = courseService;
        this.contentRepository = contentRepository;
        this.courseRepository = courseRepository;
    }

    @Override
    public void createContent(Long courseId,
                              Content content,
                              String authorizationHeader) {

        Course course = courseService.getCourseByCourseId(courseId);

        content.setCourse(course);

        contentRepository.save(content);

    }

    @Override
    public Content getContentById(Long id) {
        return null;
    }


    @Override
    public boolean updateContentById(Long id, Content content) {
        return false;
    }

    @Override
    public boolean deleteContentById(Long id) {
        return false;
    }

    @Override
    public Set<Content> getContentByCourseId(Long id) {
        Course course = courseRepository.findById(id).orElseThrow(null);
        return course.getContents();
    }
}

