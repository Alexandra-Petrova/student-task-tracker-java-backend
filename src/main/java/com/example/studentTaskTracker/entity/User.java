package com.example.studentTaskTracker.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role = Role.ROLE_USER;

    @JsonIgnore
    @OneToMany(mappedBy = "userOwner")
    private List<Task> tasks = new ArrayList<>();

    // конструктор пустой для JPA
    public User() {

    }

    public User (String email, String password) {
        this.email = email;
        this.password = password;
    }
}
