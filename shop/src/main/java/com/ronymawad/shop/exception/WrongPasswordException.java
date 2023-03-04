package com.ronymawad.shop.exception;

public class WrongPasswordException extends Exception {
    public WrongPasswordException(){
        super("Wrong password! Try again or reset password by clicking on 'forgot password?' ");
    }
}
