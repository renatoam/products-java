package com.example.todoapp.developer.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "Developers")
@Data
public class Developer extends RepresentationModel<Developer> implements Serializable {
    public static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;
    @NotBlank
    private String name;
    @NotBlank
    @Column(unique = true)
    private String email;
    private String stack;
    private String expertise;
    private UUID projectId;
}
