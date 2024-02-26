package org.example.gatewayservice.repository;

import org.example.gatewayservice.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PermissionRepository extends JpaRepository<Permission, Long> {

    Optional<Permission> getPermissionByName (String name);
}
