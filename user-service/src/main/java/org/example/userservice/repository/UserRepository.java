package org.example.userservice.repository;

import org.example.userservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends BaseIndexEntityRepo<User> {
    Optional<User> findUserByUsername(String userName);
}
