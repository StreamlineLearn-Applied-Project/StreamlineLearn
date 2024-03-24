package com.StreamlineLearn.UserManagement.service;

import com.StreamlineLearn.UserManagement.model.Administrative;

import java.util.List;

public interface AdministrativeService {

    void createAdministrative(Administrative newAdministrative);

    List<Administrative> getAllAdministrative();

    Administrative getAdministrativeById(Long id);

    boolean updateAdministrative(Long id, Administrative updateAdministrative);

    boolean deleteAdministrativeById(Long id);
}
