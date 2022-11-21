package com.example.school.myExceptions;

public class EmailValidException extends RuntimeException{
    public EmailValidException() {
        super("Write your email correctly");
    }
}
