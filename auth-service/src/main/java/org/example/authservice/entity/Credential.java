package org.example.authservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.authservice.util.EncryptUtils;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Credential extends IndexableEntity{
    @Column(unique = true)
    private Long userCredential;

    @Convert(converter = EncryptUtils.class)
    private String aesKey;
}
