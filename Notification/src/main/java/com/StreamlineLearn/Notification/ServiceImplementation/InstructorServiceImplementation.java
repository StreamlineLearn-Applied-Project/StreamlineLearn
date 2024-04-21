package com.StreamlineLearn.Notification.ServiceImplementation;



import com.StreamlineLearn.Notification.model.Instructor;
import com.StreamlineLearn.Notification.repository.InstructorRepository;
import com.StreamlineLearn.Notification.service.InstructorService;
import com.StreamlineLearn.SharedModule.dto.UserSharedDto;
import org.springframework.stereotype.Service;

@Service
public class InstructorServiceImplementation implements InstructorService {

    private final InstructorRepository instructorRepository;


    public InstructorServiceImplementation(InstructorRepository instructorRepository) {
        this.instructorRepository = instructorRepository;
    }

    @Override
    public Instructor findInstructorById(Long id) {
        return instructorRepository.findById(id).orElseThrow(null);
    }

    public void saveInstructor(UserSharedDto userDtoEvent) {
        // Check if the instructor already exists

        Instructor existingInstructor = findInstructorById(userDtoEvent.getId());

        if (existingInstructor != null) {
            // If instructor already exists, return existingInstructor or handle the case accordingly
        } else {
            // Create a new instructor object
            Instructor instructor = new Instructor();
            instructor.setId(userDtoEvent.getId());
            instructor.setUsername(userDtoEvent.getUserName());
            instructor.setRole(userDtoEvent.getRole());


            // Save the new instructor
             instructorRepository.save(instructor);
        }
    }


}
