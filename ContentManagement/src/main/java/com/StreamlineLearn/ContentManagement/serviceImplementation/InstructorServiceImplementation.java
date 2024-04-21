package com.StreamlineLearn.ContentManagement.serviceImplementation;

import com.StreamlineLearn.ContentManagement.model.Instructor;
import com.StreamlineLearn.ContentManagement.repository.InstructorRepository;
import com.StreamlineLearn.ContentManagement.service.InstructorService;
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
