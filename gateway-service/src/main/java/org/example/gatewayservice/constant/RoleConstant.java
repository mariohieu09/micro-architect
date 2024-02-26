package org.example.gatewayservice.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Collections;
import java.util.Set;

@Getter
@RequiredArgsConstructor
public enum RoleConstant {

    ADMIN(Set.of(
                 "GET_USER","PUT_USER","GET_ALL_USER","DELETE_USER"
            ))
    , USER(Collections.emptySet())
    ;

    private final Set<String> defaultPermission;
}


