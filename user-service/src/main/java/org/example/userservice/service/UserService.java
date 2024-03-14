package org.example.userservice.service;

import org.example.dto.UserResponse;
import org.example.userservice.dto.UserRequest;
import org.example.userservice.entity.User;
import org.example.userservice.repository.UserRepository;
import org.example.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestTemplate restTemplate;


    private static final String URL_USER_REGISTRATION = "http://localhost:8080/api/auth/register";

    public String createUser(UserRequest userRequest) {
        ResponseEntity<String> response = restTemplate.postForEntity(URL_USER_REGISTRATION, userRequest, String.class);
        return response.getBody();
    }


    public UserResponse getMyProfile(String bearer, Long id) {
        JwtUtils jwtUtils = new JwtUtils();
        String userName = jwtUtils.extractUsername(bearer.substring(7));
        Optional<User> userOptional = userRepository.findUserByUsername(userName);
        UserResponse userResponse = new UserResponse();
        if(userOptional.isPresent()){
            User user = userOptional.get();
            if(user.getId().equals(id)){
                userResponse = UserResponse.builder()
                        .email(user.getEmail())
                        .firstName(user.getFirstName())
                        .lastName(user.getLastName())
                        .id(user.getId())
                        .username(user.getUsername())
                        .build();
            }else{
                throw new IllegalArgumentException("Can't access!");
            }
        }
        return userResponse;
    }
}
