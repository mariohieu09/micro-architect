package org.example.gatewayservice.service;

import jakarta.annotation.PostConstruct;
import org.example.gatewayservice.constant.PermissionConstant;
import org.example.gatewayservice.entity.Permission;
import org.example.gatewayservice.repository.PermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Optional;

import static org.example.gatewayservice.constant.PermissionConstant.PERMISSION_CONSTANT;

@Service
@Order(Integer.MIN_VALUE)
public class PermissionService {

    @Autowired
    private PermissionRepository permissionRepository;

    @Transactional
    @PostConstruct
    public void ensurePermission(){
        for(Map.Entry<String, String> entry : PERMISSION_CONSTANT.getPermissions().entrySet()){
            Optional<Permission> permissionOptional = permissionRepository.getPermissionByName(entry.getValue());
            if(permissionOptional.isEmpty()){
                permissionRepository.save(Permission.builder()
                                .endpoint(entry.getKey())
                                .name(entry.getValue())
                        .build());
            }
        }
    }




}
