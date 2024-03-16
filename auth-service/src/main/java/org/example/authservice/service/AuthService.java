package org.example.authservice.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.example.authservice.dto.AuthorizeRequest;
import org.example.authservice.dto.ResponseDto;
import org.example.authservice.entity.*;
import org.example.authservice.exception.TokenExpiredToken;
import org.example.authservice.repository.CredentialRepository;
import org.example.authservice.repository.RoleRepository;
import org.example.authservice.util.EncryptUtils;
import org.example.authservice.util.JwtUtils;
import org.example.dto.UserRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.example.authservice.constant.RoleConstant.USER;

@Service
@RequiredArgsConstructor
public class AuthService {

    //TODO Decrypt the data




    private final RoleRepository roleRepository;

    private final JwtUtils jwtUtils;

    private final CredentialRepository credentialRepository;

    private final static String REGISTER_URL = "http://localhost:8080/api/user/register";

    private final static String LOGIN_URL = "http://localhost:8080/api/user/login";

    private final RestTemplate restTemplate;


    public ResponseDto register(UserRequest requestDto) throws Exception{

        ResponseEntity<User> responseEntity = restTemplate.postForEntity(REGISTER_URL, requestDto, User.class);
        User user = responseEntity.getBody();
        assert user != null;
        ExtraPermission extraPermission = ExtraPermission.builder()
                .user_id(user.getId())
                .build();
        Credential credential = Credential.builder()
                .aesKey(user.getPassword())
                .userCredential(user.getId())
                .build();
        credentialRepository.save(credential);
        Role userRole = roleRepository.getRoleByName(USER.name());
        Set<Permission> permissions = userRole.getPermissionSet();
        String accessToken = jwtUtils.generateToken(user, permissions, userRole);
        String refreshToken = jwtUtils.generateRefreshToken(accessToken);
        String tokenId = jwtUtils.getTokenId(accessToken);
        return ResponseDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .tokenId(tokenId)
                .build();
    }
//TODO
    public ResponseDto authenticate(UserRequest requestDto){
        ResponseEntity<User> responseEntity = restTemplate.postForEntity(LOGIN_URL, requestDto, User.class);
        User user = responseEntity.getBody();
        assert user != null;
        Role role = roleRepository.getRoleByName(user.getRole());
        String accessToken = jwtUtils.generateToken(user, role.getPermissionSet(), role);
        String refreshToken = jwtUtils.generateRefreshToken(accessToken);
        String tokenId = jwtUtils.getTokenId(accessToken);
        return ResponseDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .tokenId(tokenId)
                .build();
    }


    private boolean passwordMatching(String key, String encryptedPassword, String rawPassword){
        return EncryptUtils.AESDecrypt(encryptedPassword, key).equals(rawPassword);
    }

    public boolean authorizeRequest(@NonNull AuthorizeRequest request) throws TokenExpiredToken {
        boolean isAuthorize = false;
        boolean isTokenAlive = jwtUtils.isTokenExpired(request.getRefreshToken());
        if(!isTokenAlive) throw new TokenExpiredToken();
        List<String> permissionList = jwtUtils.extractPermission(request.getAccessToken());
        if(permissionList.contains(request.getCheckingPermission())){
            isAuthorize = true;
        }
        return isAuthorize;
    }


    public String getRole(String type, String value) throws Exception {
        return switch (type) {
            case "id" -> roleRepository.findById(Long.getLong(value)).get().getName();
            case "name" -> roleRepository.getRoleByName(value).getName();
            default -> throw new Exception("Can't find Role!");
        };
    }

    public String getCredential(Long userId) throws Exception {
        Optional<Credential> credentialOptional = credentialRepository.getCredentialByUserCredential(userId);
        if(credentialOptional.isEmpty()){
            throw new Exception("Credential not exist!");
        }
        return credentialOptional.get().getAesKey();
    }


}

