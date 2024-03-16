package org.example.userservice.service;

import org.example.dto.RequestDto;
import org.example.dto.ResponseDto;
import org.example.dto.UserRequest;
import org.example.dto.UserResponse;
import org.example.exception.UserNameExistedException;
import org.example.userservice.entity.User;
import org.example.userservice.repository.UserRepository;
import org.example.utils.EncryptUtils;
import org.example.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.Optional;

@Service
public class UserService implements IUserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestTemplate restTemplate;

    private final static String USER_ROLE = "USER";


    private final static String QUES = "?";

    private final static String SLASH = "/";

    private final static String AND = "&";

    private final static String ROLE_TYPE_NAME = "type=name";

    private final static String ROLE_VALUE_USER  = "value=USER";



    private static final String URL_USER_REGISTRATION = "http://localhost:8080/api/auth/getRole";

    private static final String URL_USER_GET_CREDENTIAL = "http://localhost:8080/api/auth/getCredential";
    @Override
    public User createUser(UserRequest userRequest) throws UserNameExistedException {
        Optional<User> userOptional = userRepository.findUserByUsername(userRequest.getUsername());
        if(userOptional.isPresent()) throw new UserNameExistedException("Username is exist!", new Date());
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(URL_USER_REGISTRATION + QUES + ROLE_TYPE_NAME + AND + ROLE_VALUE_USER, String.class);
        String role = responseEntity.getBody();
        User user = User
                .builder()
                .username(userRequest.getUsername())
                .password(EncryptUtils.AESEncrypt(userRequest.getPassword(), userRequest.getPassword()))
                .firstName(userRequest.getFirstName())
                .lastName(userRequest.getLastName())
                .email(userRequest.getEmail())
                .role(role)
                .build();
         userRepository.save(user);
         user.setPassword(userRequest.getPassword());
         return user;
    }

    @Override
    public UserResponse updateUser(UserRequest userRequest) {
        User user = this.update(convertToEntity(userRequest));
        return (UserResponse) convertToDto(user);
    }

    @Override
    public UserResponse getUserById(Long id) {
        return null;
    }

    @Override
    public User checkUser(RequestDto userRequest) throws Exception {
        Optional<User> userOptional = userRepository.findUserByUsername(userRequest.getUsername());
        if(userOptional.isEmpty()) throw new Exception("Can't find user");
        User user = userOptional.get();
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(URL_USER_GET_CREDENTIAL + SLASH + user.getId(), String.class);
        String aesKey = responseEntity.getBody();
        String rawPassword = userRequest.getPassword();
        String decryptedPassword = EncryptUtils.AESDecrypt(user.getPassword(), aesKey);
        if(!rawPassword.equals(decryptedPassword)){
            throw new Exception("Bad Credential!");
        }
        return user;
    }

    @Override
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

    @Override
    public Optional<User> getById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public User create(User entity) {
        return null;
    }

    @Override
    public Iterable<User> getAll() {
        return null;
    }

    @Override
    public User update(User entity) {
        return userRepository.save(entity);
    }

    @Override
    public void deleteById(Long id) {

    }



    public User convertToEntity(UserRequest userRequest) {
        return User.builder()
                .email(userRequest.getEmail())
                .firstName(userRequest.getFirstName())
                .lastName(userRequest.getLastName())
                .password(userRequest.getPassword())
                .build();
    }


    public UserResponse convertToDto(User user) {
        return UserResponse.builder()
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .id(user.getId())
                .lastName(user.getLastName())
                .username(user.getUsername())
                .build();
    }
}
