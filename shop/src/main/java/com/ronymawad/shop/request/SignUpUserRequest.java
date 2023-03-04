package com.ronymawad.shop.request;

public record SignUpUserRequest(
        String firstName,
        String lastName,
        String email,
        String password,
        String roles
) {
}
