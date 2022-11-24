package com.example.school.student.dao.model;

import java.util.UUID;

public class StudentDTO {

    private final String name;
    private final String lastName;
    private final int age;
    private final String email;
    private final String fieldOfStudy;
    private final String uuid;

    public StudentDTO(String name, String lastName, int age, String email, String fieldOfStudy) {
        this.name = name;
        this.lastName = lastName;
        this.age = age;
        this.email = email;
        this.fieldOfStudy = fieldOfStudy;
        this.uuid = UUID.randomUUID().toString();
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public int getAge() {
        return age;
    }

    public String getEmail() {
        return email;
    }

    public String getFieldOfStudy() {
        return fieldOfStudy;
    }

    public String getUuid() {
        return uuid;
    }

    @Override
    public String toString() {
        return "StudentDTO{" +
                "name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                ", email='" + email + '\'' +
                ", fieldOfStudy='" + fieldOfStudy + '\'' +
                ", uuid='" + uuid + '\'' +
                '}';
    }
}
