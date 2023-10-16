package com.example.todoapp.developer.controllers;

import com.example.todoapp.developer.models.Developer;
import com.example.todoapp.developer.repositories.DeveloperRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/developers")
public class DeveloperController {
    private DeveloperRepository repository;

    DeveloperController(DeveloperRepository repository) {
        this.repository = repository;
    }

    @GetMapping()
    public ResponseEntity getDevelopers() {
        List<Developer> developers = this.repository.findAll();

        if (developers.size() == 0) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("There is no developer registered.");
        }

        return ResponseEntity.status(HttpStatus.OK).body(developers);
    }
}
