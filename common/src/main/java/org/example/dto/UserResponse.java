package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse extends BaseDto{

    private Long id;

    private String username;

    private String lastName;

    private String firstName;
    private String email;
}
