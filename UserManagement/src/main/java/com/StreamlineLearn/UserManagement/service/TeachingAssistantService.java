package com.StreamlineLearn.UserManagement.service;

import com.StreamlineLearn.UserManagement.model.TeachingAssistant;

import java.util.List;

public interface TeachingAssistantService {

    void createTeachingAssistant(TeachingAssistant newTeachingAssistant);

    List<TeachingAssistant> getAllTeachingAssistant();

    TeachingAssistant getTeachingAssistantById(Long id);

    boolean updateTeachingAssistant(Long id, TeachingAssistant teachingAssistant);

    boolean deleteTeachingAssistantById(Long id);
}
