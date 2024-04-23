package com.StreamlineLearn.DiscussionService.service;

import com.StreamlineLearn.DiscussionService.model.Instructor;
import com.StreamlineLearn.SharedModule.dto.UserSharedDto;

public interface InstructorService {
    public Instructor findInstructorById(Long id);

    void saveInstructor(UserSharedDto userDtoEvent);


}
