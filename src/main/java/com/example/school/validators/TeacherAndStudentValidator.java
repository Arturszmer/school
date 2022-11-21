package com.example.school.validators;

public interface TeacherAndStudentValidator {

    boolean nameLengthValid(String name);
    boolean ageValid(int age);
    boolean emailValid(String email);

}
