package com.sutoga.backend.entity;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "user")
public class User {

    @Id
    private Long id;

    private String firstName;

    private String lastName;

    private String username;

    private String email;

    private String password;

    private String phoneNumber;

    private String gender;

    private LocalDate birthDate;

    private List<User> friends;
}