package org.example.dto;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequest extends BaseDto{

    private String username;


    private String password;

    private String lastName;

    private String firstName;
    @Email
    private String email;

}
