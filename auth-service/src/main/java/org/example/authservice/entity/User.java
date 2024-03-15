package org.example.authservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class User{

    private Long id;
    private String username;


    private String password;



    private String firstName;

    private String lastName;

    private String email;

    private String role;
}
