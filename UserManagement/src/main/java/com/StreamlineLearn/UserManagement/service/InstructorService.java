package com.StreamlineLearn.UserManagement.service;

import com.StreamlineLearn.UserManagement.model.Instructor;

import java.util.List;

public interface InstructorService {
    public void createInstructor(Instructor newInstructor);

    public List<Instructor> getAllInstructor();

    public Instructor getInstructorById(Long id);

    public boolean updateInstructor(Long id, Instructor updateInstructor);

    public boolean deleteInstructorById(Long id);
}
