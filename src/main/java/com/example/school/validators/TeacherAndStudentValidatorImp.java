package com.example.school.validators;

import com.example.school.myExceptions.AgeValidException;
import com.example.school.myExceptions.EmailValidException;
import com.example.school.myExceptions.NameValidException;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class TeacherAndStudentValidatorImp implements TeacherAndStudentValidator {

    @Override
    public boolean nameLengthValid(String name) {
        if (name.length() < 3){
            throw new NameValidException();
        } else return true;
    }

    @Override
    public boolean ageValid(int age) {
        if (age <= 18){
            throw new AgeValidException();
        } else return true;
    }

    @Override
    public boolean emailValid(String email) {
        String regexPattern = "[\\w]{1,20}@\\w{2,20}\\.\\w{2,3}$";
        if (!patternMatches(email, regexPattern)){
            throw new EmailValidException();
        } else return true;
    }

    private boolean patternMatches(String emailAddress, String regexPattern) {
        return Pattern.compile(regexPattern)
                .matcher(emailAddress)
                .matches();
    }
}
