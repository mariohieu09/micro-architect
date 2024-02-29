package org.example.userservice.controller;

import lombok.RequiredArgsConstructor;
import org.example.userservice.dto.UserRequest;
import org.example.userservice.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    @GetMapping("/get")
    public String getUser(){
        return "User:Get";
    }
    @PostMapping("/post")
    public String postUser(@RequestBody UserRequest userRequest){
        return userService.createUser(userRequest);
    }
    @PutMapping("/put")
    public String putUser(){
        return "User:Put";
    }
    @GetMapping("/get-all")
    public String getAllUser(){
        return "User:GetAll";
    }

    @DeleteMapping("/delete")
    public String deleteUser(){
        return "User:Delete";
    }

}
