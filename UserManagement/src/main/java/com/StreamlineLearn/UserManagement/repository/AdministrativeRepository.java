package com.StreamlineLearn.UserManagement.repository;

import com.StreamlineLearn.UserManagement.model.Administrative;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdministrativeRepository extends JpaRepository<Administrative, Long> {
    Optional<Administrative> findByUserId(Long userId);
}
