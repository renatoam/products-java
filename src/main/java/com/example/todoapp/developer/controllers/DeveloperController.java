package com.example.todoapp.developer.controllers;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.example.todoapp.common.BaseController;
import com.example.todoapp.developer.dtos.CreateDevDTO;
import com.example.todoapp.developer.dtos.UpdateDevDTO;
import com.example.todoapp.developer.models.Developer;
import com.example.todoapp.developer.repositories.DeveloperRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(BaseController.API_VERSION + "/developers")
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

    @GetMapping("{id}")
    public ResponseEntity getDeveloperById(@PathVariable UUID id) {
        Optional<Developer> developers = this.repository.findById(id);

        if (developers.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("There is no developer with such ID.");
        }

        return ResponseEntity.status(HttpStatus.OK).body(developers);
    }

    @PostMapping
    public ResponseEntity createDeveloper(@RequestBody @Valid CreateDevDTO createDevDTO) {
        Developer developer = this.repository.findByEmail(createDevDTO.email());

        if (developer != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("There is already a developer with this email.");
        }

        Developer newDeveloper = new Developer();
        BeanUtils.copyProperties(createDevDTO, newDeveloper);

        this.repository.save(newDeveloper);
        return ResponseEntity.status(HttpStatus.CREATED).body(newDeveloper);
    }

    @PutMapping("{email}")
    public ResponseEntity updateDeveloper(@PathVariable String email, @RequestBody @Valid UpdateDevDTO updateDevDTO) {
        Developer developer = this.repository.findByEmail(email);

        if (developer == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("There is no developer with this email.");
        }

        BeanUtils.copyProperties(updateDevDTO, developer);
        this.repository.save(developer);
        return ResponseEntity.status(HttpStatus.OK).body(developer);
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteDeveloper(@PathVariable UUID id) {
        Optional<Developer> developer = this.repository.findById(id);

        if (developer.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("There is no developer with this ID.");
        }

        this.repository.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body("Developer successfully deleted.");
    }
}
