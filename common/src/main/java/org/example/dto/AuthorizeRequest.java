package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthorizeRequest {
    /**
     * This is the token gateway sends
     */

    private String token;


    /**
     *  This is the permission to check
     */
    private String checkingPermission;
}
