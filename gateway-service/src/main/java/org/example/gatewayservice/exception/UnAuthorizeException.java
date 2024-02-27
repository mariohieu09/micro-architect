package org.example.gatewayservice.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UnAuthorizeException extends RuntimeException {

    private String message;

    private Date timestamp;
}
