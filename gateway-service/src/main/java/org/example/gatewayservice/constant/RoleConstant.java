package org.example.gatewayservice.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Collections;
import java.util.Set;

@Getter
@RequiredArgsConstructor
public enum RoleConstant {

    ADMIN(Set.of(
                 "GET_USER","PUT_USER","GET_ALL_USER","DELETE_USER", "GET_PRODUCT"
            ))
    , USER(Set.of(
            "GET_PRODUCT"
    ))
    ;

    private final Set<String> defaultPermission;
}


