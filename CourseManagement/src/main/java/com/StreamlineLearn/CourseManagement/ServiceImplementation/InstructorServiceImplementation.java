package com.StreamlineLearn.CourseManagement.ServiceImplementation;

import com.StreamlineLearn.CourseManagement.model.Instructor;
import com.StreamlineLearn.CourseManagement.repository.InstructorRepository;
import com.StreamlineLearn.CourseManagement.service.InstructorService;
import com.StreamlineLearn.SharedModule.dto.UserDto;
import org.springframework.stereotype.Service;

@Service
public class InstructorServiceImplementation implements InstructorService {

    private final InstructorRepository instructorRepository;


    public InstructorServiceImplementation(InstructorRepository instructorRepository) {
        this.instructorRepository = instructorRepository;
    }

    @Override
    public Instructor findInstructorByInstructorId(Long instructorId) {
        return instructorRepository.findById(instructorId).orElse(null);
    }

    public void saveInstructor(UserDto userDtoEvent) {
        // Check if the instructor already exists
        Instructor existingInstructor = findInstructorByInstructorId(userDtoEvent.getId());

        if (existingInstructor != null) {
            // If instructor already exists, return existingInstructor or handle the case accordingly
        } else {
            // Create a new instructor object
            Instructor instructor = new Instructor();
            instructor.setInstructorId(userDtoEvent.getId()); // Assuming you set the ID here, adjust as necessary
            instructor.setUsername(userDtoEvent.getUserName()); // Assuming you set the username here, adjust as necessary
            instructor.setRole(userDtoEvent.getRole());

            // Save the new instructor
             instructorRepository.save(instructor);
        }
    }


}
