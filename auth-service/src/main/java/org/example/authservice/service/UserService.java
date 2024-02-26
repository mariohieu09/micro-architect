package org.example.authservice.service;

import lombok.RequiredArgsConstructor;
import org.example.authservice.constant.RoleConstant;
import org.example.authservice.dto.RequestDto;
import org.example.authservice.dto.ResponseDto;
import org.example.authservice.entity.Credential;
import org.example.authservice.entity.Role;
import org.example.authservice.entity.User;
import org.example.authservice.repository.CredentialRepository;
import org.example.authservice.repository.RoleRepository;
import org.example.authservice.repository.UserRepository;
import org.example.authservice.util.EncryptUtils;
import org.example.authservice.util.JwtUtils;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.example.authservice.constant.RoleConstant.ADMIN;
import static org.example.authservice.constant.RoleConstant.USER;

@Service
@RequiredArgsConstructor
public class UserService {


    private final UserRepository repository;

    private final RoleRepository roleRepository;

    private final JwtUtils jwtUtils;

    private final CredentialRepository credentialRepository;



    public ResponseDto register(RequestDto requestDto){
        Role role = roleRepository.getRoleByName(ADMIN.name());
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
                .encryptData(jwtUtils.generateToken(Map.of("credential", role.getPermissionSet()), user))
                .build();
    }


}
