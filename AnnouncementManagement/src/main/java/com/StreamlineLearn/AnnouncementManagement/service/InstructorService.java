package com.StreamlineLearn.AnnouncementManagement.service;

import com.StreamlineLearn.AnnouncementManagement.model.Instructor;
import com.StreamlineLearn.SharedModule.dto.UserSharedDto;

public interface InstructorService {
    public Instructor findInstructorById(Long id);

    void saveInstructor(UserSharedDto userDtoEvent);



}
