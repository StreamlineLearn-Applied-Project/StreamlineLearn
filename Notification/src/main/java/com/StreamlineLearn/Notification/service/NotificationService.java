package com.StreamlineLearn.Notification.service;

import com.StreamlineLearn.Notification.model.Notification;
import com.StreamlineLearn.SharedModule.dto.CourseSharedDto;
import com.StreamlineLearn.SharedModule.dto.EnrolledStudentDto;

import java.util.List;
import java.util.Optional;

public interface NotificationService {
    public void notifyCourseCreated(CourseSharedDto courseSharedDto);
    public void notifyStudentEnrolled(EnrolledStudentDto enrolledStudentDto);
    List<Notification> getStudentNotifications(String authorizationHeader);
    List<Notification> getInstructorNotifications(String authorizationHeader);
}
