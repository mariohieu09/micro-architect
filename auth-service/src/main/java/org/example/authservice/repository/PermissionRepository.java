package org.example.authservice.repository;

import org.example.authservice.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
public interface PermissionRepository extends JpaRepository<Permission, Long> {

}
