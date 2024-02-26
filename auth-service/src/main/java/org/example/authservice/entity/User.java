package org.example.authservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User extends IndexableEntity{
    private String username;


    private String password;


    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Permission> extraPermission;

    @ManyToOne(fetch = FetchType.EAGER)
    private Role role;

}
