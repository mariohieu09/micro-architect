package org.example.userservice.entity;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User extends IndexableEntity {


    private String role;

    private String username;

    private String password;


    private String firstName;

    private String lastName;

    private String email;

}
