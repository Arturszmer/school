package com.example.school.myExceptions;

public class AgeValidException extends RuntimeException {

    public AgeValidException() {
        super("Age has to be higher than 18.");
    }
}
