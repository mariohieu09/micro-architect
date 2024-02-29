package org.example.userservice.service;

import org.example.userservice.dto.UserRequest;
import org.example.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestTemplate restTemplate;


    private static final String URL_USER_REGISTRATION = "http://localhost:8080/api/auth/register";

    public String createUser(UserRequest userRequest){
        ResponseEntity<String> response = restTemplate.postForEntity(URL_USER_REGISTRATION, userRequest, String.class);
        return response.getBody();
    }


}
