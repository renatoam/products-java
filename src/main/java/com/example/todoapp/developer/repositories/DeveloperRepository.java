package com.example.todoapp.developer.repositories;

import com.example.todoapp.developer.models.Developer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DeveloperRepository extends JpaRepository<Developer, UUID> {
    Developer findByEmail(String email);
    Developer deleteByEmail(String email);
}
