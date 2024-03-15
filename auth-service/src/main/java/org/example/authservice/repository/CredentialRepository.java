package org.example.authservice.repository;

import org.example.authservice.entity.Credential;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CredentialRepository extends JpaRepository<Credential, Long> {

    Optional<Credential> getCredentialByUserCredential(Long user_id);
}
