package org.example.gatewayservice.service;

import com.google.common.collect.Multiset;
import jakarta.annotation.PostConstruct;
import org.example.gatewayservice.constant.RoleConstant;
import org.example.gatewayservice.entity.Permission;
import org.example.gatewayservice.entity.Role;
import org.example.gatewayservice.repository.PermissionRepository;
import org.example.gatewayservice.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@DependsOn("permissionService")
public class RoleService {

    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private RoleRepository roleRepository;

    @PostConstruct
    @Transactional
    public void ensureRole() throws RuntimeException{
        for(RoleConstant roleConstant : RoleConstant.values()){
            Optional<Role> roleOptional = roleRepository.getRoleByName(roleConstant.name());
            if(roleOptional.isPresent()){
                Role role = roleOptional.get();
                Set<Permission> permissionSet = new HashSet<>(roleConstant.getDefaultPermission().stream()
                        .map(s -> {
                            try {
                                return this.getPermissionByName(s);
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                        })
                        .toList());
                role.setPermissionSet(permissionSet);
                roleRepository.save(role);
            }else{
                Set<Permission> permissionSet = new HashSet<>(roleConstant.getDefaultPermission().stream()
                        .map(s -> {
                            try {
                                return this.getPermissionByName(s);
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                        })
                        .toList());
                roleRepository.save(Role.builder()
                                .name(roleConstant.name())
                                .permissionSet(permissionSet)
                        .build());
            }
        }
    }


    private Permission getPermissionByName(String name) throws Exception{
        Optional<Permission> permissionOptional = permissionRepository.getPermissionByName(name);
        if(permissionOptional.isEmpty()) throw new Exception("Permission not exist!");
        return permissionOptional.get();
    }
}
