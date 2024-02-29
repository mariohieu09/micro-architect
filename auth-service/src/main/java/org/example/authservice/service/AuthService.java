package org.example.authservice.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.example.authservice.dto.AuthorizeRequest;
import org.example.authservice.dto.RequestDto;
import org.example.authservice.dto.ResponseDto;
import org.example.authservice.entity.Credential;
import org.example.authservice.entity.User;
import org.example.authservice.exception.BadCredentialException;
import org.example.authservice.exception.UserNameExistedException;
import org.example.authservice.exception.UsernameNotFoundException;
import org.example.authservice.repository.CredentialRepository;
import org.example.authservice.repository.RoleRepository;
import org.example.authservice.repository.UserRepository;
import org.example.authservice.util.EncryptUtils;
import org.example.authservice.util.JwtUtils;
import org.springframework.stereotype.Service;

import java.util.*;

import static org.example.authservice.constant.RoleConstant.USER;

@Service
@RequiredArgsConstructor
public class AuthService {

    //TODO Decrypt the data
    private final UserRepository repository;

    private final RoleRepository roleRepository;

    private final JwtUtils jwtUtils;

    private final CredentialRepository credentialRepository;



    public ResponseDto register(RequestDto requestDto) throws Exception{
        Optional<User> userOptional = repository.getUserByUsername(requestDto.getUsername());
        if(userOptional.isPresent()) throw new UserNameExistedException("Username is exist!", new Date());
        User user = User
                .builder()
                .username(requestDto.getUsername())
                .password(EncryptUtils.AESEncrypt(requestDto.getPassword(), requestDto.getPassword()))
                .extraPermission(Collections.emptySet())
                .role(roleRepository.getRoleByName(USER.name()))
                .build();
        User savedUser = repository.save(user);
        credentialRepository.save(Credential.builder()
                        .userCredential(savedUser.getId())
                        .aesKey(requestDto.getPassword())
                .build());
        return ResponseDto.builder()
                .encryptData(jwtUtils.generateToken(user))
                .build();
    }

    public ResponseDto authenticate(RequestDto requestDto) throws Exception {
        Optional<User> userOptional = repository.getUserByUsername(requestDto.getUsername());
        //Username not found!
        if(userOptional.isEmpty()) throw new UsernameNotFoundException("Username not found!", new Date());
        User user = userOptional.get();
        Credential credential = credentialRepository.findById(user.getId()).get();
        boolean isGood = passwordMatching(credential.getAesKey(), user.getPassword(), requestDto.getPassword());
        //This exception causes when the password is not match
        if(!isGood) throw new BadCredentialException("Bad credential", new Date());
        return ResponseDto.builder()
                .encryptData(jwtUtils.generateToken(user))
                .build();
    }


    private boolean passwordMatching(String key, String encryptedPassword, String rawPassword){
        return EncryptUtils.AESDecrypt(encryptedPassword, key).equals(rawPassword);
    }

    public boolean authorizeRequest(@NonNull AuthorizeRequest request){
        boolean isAuthorize = false;
        List<String> permissionList = jwtUtils.extractPermission(request.getToken());
        if(permissionList.contains(request.getCheckingPermission())){
            isAuthorize = true;
        }
        return isAuthorize;
    }


}

