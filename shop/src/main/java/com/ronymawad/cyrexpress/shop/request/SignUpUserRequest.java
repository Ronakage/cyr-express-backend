package com.ronymawad.cyrexpress.shop.request;

public record SignUpUserRequest(
        String firstName,
        String lastName,
        String email,
        String password,
        String roles
) {
}
