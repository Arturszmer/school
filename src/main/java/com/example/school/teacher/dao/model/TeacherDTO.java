package com.example.school.teacher.dao.model;

import com.example.school.student.dao.model.StudentDTO;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class TeacherDTO {

    private final String name;
    private final String lastName;
    private final int age;
    private final String email;
    private final String subject;
    private final String uuid;
    private final List<StudentDTO> studentDTOS;

    public TeacherDTO(String name, String lastName, int age, String email, String subject, List<StudentDTO> studentDTOS) {
        this.name = name;
        this.lastName = lastName;
        this.age = age;
        this.email = email;
        this.subject = subject;
        this.uuid = UUID.randomUUID().toString();
        this.studentDTOS = studentDTOS;
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

    public List<StudentDTO> getStudentDTOS() {
        return Collections.unmodifiableList(studentDTOS);
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
                ", studentDTOS=" + studentDTOS +
                '}';
    }
}
