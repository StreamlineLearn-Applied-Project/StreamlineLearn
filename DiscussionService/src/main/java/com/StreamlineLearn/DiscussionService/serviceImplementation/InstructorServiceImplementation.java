package com.StreamlineLearn.DiscussionService.serviceImplementation;


import com.StreamlineLearn.DiscussionService.model.Instructor;
import com.StreamlineLearn.DiscussionService.repository.InstructorRepository;
import com.StreamlineLearn.DiscussionService.service.InstructorService;
import com.StreamlineLearn.SharedModule.dto.UserSharedDto;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class InstructorServiceImplementation implements InstructorService {

    private final InstructorRepository instructorRepository;


    public InstructorServiceImplementation(InstructorRepository instructorRepository) {
        this.instructorRepository = instructorRepository;
    }

    @Override
    public Instructor findInstructorById(Long id) {
        return instructorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Instructor not found with id: " + id));
    }

    public void saveInstructor(UserSharedDto userDtoEvent) {
        // Create a new instructor object
        Instructor instructor = new Instructor();
        instructor.setId(userDtoEvent.getId());
        instructor.setUsername(userDtoEvent.getUserName());
        instructor.setRole(userDtoEvent.getRole());

        instructorRepository.save(instructor);

    }


}
