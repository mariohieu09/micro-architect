package org.example.authservice.controller;

import lombok.RequiredArgsConstructor;
import org.example.authservice.dto.AuthorizeRequest;
import org.example.authservice.dto.RequestDto;
import org.example.authservice.dto.ResponseDto;
import org.example.authservice.exception.TokenExpiredToken;
import org.example.authservice.service.AuthService;
import org.example.dto.UserRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {


    private final AuthService authService;


    @PostMapping("/register")
    public ResponseDto register(@RequestBody UserRequest requestDto) throws Exception{
        return authService.register(requestDto);
    }

    @PostMapping("/authenticate")
    public ResponseDto authenticate(@RequestBody UserRequest requestDto) throws Exception {
        return  authService.authenticate(requestDto);
    }
    @Deprecated
    @GetMapping("/authorize")
    public boolean getAuthorizeRequest(@RequestParam("token") String token, @RequestParam("permission") String permission) throws TokenExpiredToken {
        AuthorizeRequest authorizeRequest =  AuthorizeRequest.builder()
                .accessToken(token)
                .checkingPermission(permission)
                .build();
        return authService.authorizeRequest(authorizeRequest);
    }

    @PostMapping("/authorize")
    public boolean postAuthorizeRequest(@RequestBody AuthorizeRequest request) throws TokenExpiredToken {
        return authService.authorizeRequest(request);
    }


    @GetMapping("/getRole")
    public String getRoleByName(@RequestParam("type") String type, @RequestParam("value") String value) throws Exception {
        return authService.getRole(type ,value);
    }


    @GetMapping("/getCredential/{user_id}")
    public String getCredentialByUserId(@PathVariable Long user_id) throws Exception {
        return authService.getCredential(user_id);
    }
}
