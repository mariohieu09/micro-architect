package org.example.authservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Credential extends IndexableEntity{
    @Column(unique = true)
    private Long userCredential;


    private String aesKey;
}
