package com.ronymawad.shop.exception;

public class EmailAlreadyUsedException extends Exception {
    public EmailAlreadyUsedException(String email){
        super(email +" is already used for another account.");
    }
}
