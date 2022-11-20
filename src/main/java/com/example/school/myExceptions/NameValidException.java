package com.example.school.myExceptions;

public class NameValidException extends RuntimeException {
    public NameValidException() {
        super("Name has to get more than 2 letters.");
    }
}
