package com.example.school.student.dao.model;

import com.example.school.teacher.dao.model.TeacherDTO;

import java.util.List;
import java.util.UUID;

public class StudentDTO {

    private final String name;
    private final String lastName;
    private final int age;
    private final String email;
    private final String fieldOfStudy;
    private final String uuid;
    private final List<TeacherDTO> teacherDTOS;

    public StudentDTO(String name, String lastName, int age, String email, String fieldOfStudy, List<TeacherDTO> teacherDTOS) {
        this.name = name;
        this.lastName = lastName;
        this.age = age;
        this.email = email;
        this.fieldOfStudy = fieldOfStudy;
        this.uuid = UUID.randomUUID().toString();
        this.teacherDTOS = teacherDTOS;
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

    public List<TeacherDTO> getTeacherDTOS() {
        return teacherDTOS;
    }
}
