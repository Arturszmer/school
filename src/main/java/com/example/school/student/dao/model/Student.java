package com.example.school.student.dao.model;

import com.example.school.teacher.dao.model.Teacher;

import javax.persistence.*;
import java.util.*;

@Entity
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    private String lastName;
    private int age;
    private String email;
    private String fieldOfStudy;
    private String uuid;
    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private Set<Teacher> teachers = new HashSet<>();

    public Student() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFieldOfStudy() {
        return fieldOfStudy;
    }

    public void setFieldOfStudy(String fieldOfStudy) {
        this.fieldOfStudy = fieldOfStudy;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Set<Teacher> getTeachers() {
        return Collections.unmodifiableSet(teachers);
    }

    public void assignTeacher(Teacher teacher) {
        this.teachers.add(teacher);
    }
    public void removeTeacher(Teacher teacher){
        this.teachers.remove(teacher);
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                ", email='" + email + '\'' +
                ", fieldOfStudy='" + fieldOfStudy + '\'' +
                ", uuid='" + uuid + '\'' +
                ", teachers=" + teachers +
                '}';
    }
}
