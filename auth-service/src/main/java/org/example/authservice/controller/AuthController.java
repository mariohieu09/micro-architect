package org.example.authservice.controller;

import lombok.RequiredArgsConstructor;
import org.example.authservice.dto.RequestDto;
import org.example.authservice.dto.ResponseDto;
import org.example.authservice.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    @PostMapping("/register")
    public ResponseDto responseDto(@RequestBody RequestDto requestDto){

        return userService.register(requestDto);
    }
}
