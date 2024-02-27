package org.example.gatewayservice.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@Getter
@RequiredArgsConstructor
public enum PermissionConstant {

    PERMISSION_CONSTANT(Map.of(
            "/api/user/get", "GET_USER",
            "/api/user/put", "PUT_USER",
            "/api/user/get-all", "GET_ALL_USER",
            "/api/user/delete", "DELETE_USER",
            "/api/product", "GET_PRODUCT"
    ))
    ;
    private final Map<String, String> permissions;

}
