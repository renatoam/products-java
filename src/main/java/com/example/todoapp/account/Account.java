package com.example.todoapp.account;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;
import java.util.UUID;

@Data
@Entity
@Table(name = "Accounts")
public class Account extends RepresentationModel<Account> implements Serializable {
    public static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    private String name;
    @NotBlank
    private String password;
    @NotBlank
    @Column(unique = true)
    private String email;

    private String role = "admin";

    @JsonIgnore
    public String getPassword() {
        return password;
    }
}
