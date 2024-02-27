package org.example.authservice.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BadCredentialException extends Exception {

    private String message;

    private Date timeStamp;
}
