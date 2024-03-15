package org.example.userservice.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.RequestDto;
import org.example.dto.UserRequest;
import org.example.dto.UserResponse;
import org.example.exception.UserNameExistedException;
import org.example.userservice.entity.User;
import org.example.userservice.service.IUserService;
import org.example.userservice.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final IUserService userService;
    @GetMapping("/get/{id}")
    public UserResponse getUser(@PathVariable Long id){
        return userService.getUserById(id);
    }
    @PostMapping("/register")
    public User register(@RequestBody UserRequest userRequest) throws UserNameExistedException {
        return userService.createUser(userRequest);
    }

    @PostMapping("/login")
    public User login(@RequestBody RequestDto userRequest) throws Exception {
        return userService.checkUser(userRequest);
    }
    @PutMapping("/update")
    public UserResponse update(@RequestBody UserRequest userRequest){
        return userService.updateUser(userRequest);
    }
    @GetMapping("/get-all")
    public String getAllUser(){
        return "User:GetAll";
    }

    @DeleteMapping("/delete")
    public String deleteUser(){
        return "User:Delete";
    }

    @GetMapping("/get-profile/{id}")
    public UserResponse getMyProfile(@RequestHeader("Authorization") String bearer, @PathVariable Long id){
        return userService.getMyProfile(bearer, id);
    }

}
