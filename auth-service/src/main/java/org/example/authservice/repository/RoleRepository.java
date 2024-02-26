package org.example.authservice.repository;

import org.example.authservice.entity.Permission;
import org.example.authservice.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role getRoleByName(String name);
}
