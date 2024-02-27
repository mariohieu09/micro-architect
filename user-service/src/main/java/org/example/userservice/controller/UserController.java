package org.example.userservice.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @GetMapping("/get")
    public String getUser(){
        return "User:Get";
    }
    @PostMapping("/post")
    public String postUser(){
        return "User:Post";
    }
    @PutMapping("/put")
    public String putUser(){
        return "User:Put";
    }
    @GetMapping("/get-all")
    public String getAllUser(){
        return "User:GetAll";
    }

}
