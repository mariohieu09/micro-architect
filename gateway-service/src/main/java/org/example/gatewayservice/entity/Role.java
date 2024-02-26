package org.example.gatewayservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Role extends IndexableEntity {

    private String name;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Permission> permissionSet;
}
