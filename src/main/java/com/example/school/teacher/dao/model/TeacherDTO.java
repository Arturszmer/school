package com.example.school.teacher.dao.model;

import java.util.UUID;

public class TeacherDTO {

    private final String name;
    private final String lastName;
    private final int age;
    private final String email;
    private final String subject;
    private final String uuid;

    public TeacherDTO(String name, String lastName, int age, String email, String subject) {
        this.name = name;
        this.lastName = lastName;
        this.age = age;
        this.email = email;
        this.subject = subject;
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

    public String getSubject() {
        return subject;
    }

    public String getUuid() {
        return uuid;
    }

    @Override
    public String toString() {
        return "TeacherDTO{" +
                "name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                ", email='" + email + '\'' +
                ", subject='" + subject + '\'' +
                ", uuid='" + uuid + '\'' +
                '}';
    }
}
