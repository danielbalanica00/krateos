package com.simpolab.server_main.security.domain;


import lombok.Data;

@Data
public class User {
    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String email;
}
