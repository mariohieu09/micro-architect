package org.example.userservice.service;

import org.example.dto.RequestDto;
import org.example.dto.UserRequest;
import org.example.dto.UserResponse;
import org.example.exception.UserNameExistedException;
import org.example.userservice.entity.User;
import org.springframework.stereotype.Service;
@Service
public interface IUserService extends IGeneralService<User>{

    UserResponse getMyProfile(String bearer , Long id);

    User createUser(UserRequest userRequest) throws UserNameExistedException;


    UserResponse updateUser(UserRequest userRequest);

    UserResponse getUserById(Long id);

    User checkUser(RequestDto userRequest) throws Exception;
}
