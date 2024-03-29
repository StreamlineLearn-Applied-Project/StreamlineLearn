package com.StreamlineLearn.UserManagement.repository;

import com.StreamlineLearn.UserManagement.model.Administrative;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdministrativeRepository extends JpaRepository<Administrative, Long> {
    Administrative findByUserId(Long userId);
}
