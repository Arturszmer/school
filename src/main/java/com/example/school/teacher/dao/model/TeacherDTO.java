package com.example.school.teacher.dao.model;

import com.example.school.student.dao.model.StudentDTO;

import java.util.Collections;
import java.util.List;

public class TeacherDTO {

    private final String name;
    private final String lastName;
    private final int age;
    private final String email;
    private final String subject;
    private final List<StudentDTO> studentDTOS;

    public TeacherDTO(String name, String lastName, int age, String email, String subject, List<StudentDTO> studentDTOS) {
        this.name = name;
        this.lastName = lastName;
        this.age = age;
        this.email = email;
        this.subject = subject;
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

    public List<StudentDTO> getStudentDTOS() {
        return Collections.unmodifiableList(studentDTOS);
    }
}
