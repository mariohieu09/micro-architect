package org.example.authservice.dto;

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

    private String accessToken;

    private String refreshToken;

    private String token_id;




    /**
     *  This is the permission to check
     */
    private String checkingPermission;
}
