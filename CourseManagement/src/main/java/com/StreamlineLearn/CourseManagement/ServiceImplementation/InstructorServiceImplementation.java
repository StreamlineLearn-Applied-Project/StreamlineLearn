package com.StreamlineLearn.CourseManagement.ServiceImplementation;

import com.StreamlineLearn.CourseManagement.model.Instructor;
import com.StreamlineLearn.CourseManagement.repository.InstructorRepository;
import com.StreamlineLearn.CourseManagement.service.InstructorService;
import com.StreamlineLearn.CourseManagement.utility.JwtService;
import org.springframework.stereotype.Service;

@Service
public class InstructorServiceImplementation implements InstructorService {

    private final InstructorRepository instructorRepository;
    private final JwtService jwtService;

    public InstructorServiceImplementation(InstructorRepository instructorRepository,
                                           JwtService jwtService) {
        this.instructorRepository = instructorRepository;
        this.jwtService = jwtService;
    }

    @Override
    public Instructor findInstructorByInstructorId(Long instructorId) {
        return instructorRepository.findById(instructorId).orElse(null);
    }


    public Instructor saveInstructor(String token) {
        String username = jwtService.extractUserName(token);
        Long instructorId = jwtService.extractRoleId(token);
        String role = jwtService.extractUserRole(token);

        // Check if the instructor already exists
        Instructor existingInstructor = findInstructorByInstructorId(instructorId);

        if (existingInstructor != null) {
            // If instructor already exists, return existingInstructor or handle the case accordingly
            return existingInstructor;
        } else {
            // Create a new instructor object
            Instructor instructor = new Instructor();
            instructor.setInstructorId(instructorId); // Assuming you set the ID here, adjust as necessary
            instructor.setUsername(username); // Assuming you set the username here, adjust as necessary
            // You can set other properties if needed

            // Save the new instructor
            return instructorRepository.save(instructor);
        }
    }


}
