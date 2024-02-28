package org.example.authservice.controller;

import lombok.RequiredArgsConstructor;
import org.example.authservice.dto.AuthorizeRequest;
import org.example.authservice.dto.RequestDto;
import org.example.authservice.dto.ResponseDto;
import org.example.authservice.service.AuthService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    @PostMapping("/register")
    public ResponseDto register(@RequestBody RequestDto requestDto) throws Exception{

        return authService.register(requestDto);
    }

    @PostMapping("/authenticate")
    public ResponseDto authenticate(@RequestBody RequestDto requestDto) throws Exception {
        return  authService.authenticate(requestDto);
    }

    @GetMapping("/authorize")
    public boolean authorizeRequest(@RequestParam("token") String token, @RequestParam("permission") String permission){
        AuthorizeRequest authorizeRequest =  AuthorizeRequest.builder()
                .token(token)
                .checkingPermission(permission)
                .build();
        return authService.authorizeRequest(authorizeRequest);
    }
}
