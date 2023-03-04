package com.ronymawad.shop.exception;

public class UserNotFound extends Exception {
    public UserNotFound(String email) {
        super("User with email : " +email +" is not found!");
    }
}
