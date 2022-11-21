package com.example.school.student.service;

import com.example.school.student.repo.StudentRepo;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

    private final StudentRepo studentRepo;

    public StudentService(StudentRepo studentRepo) {
        this.studentRepo = studentRepo;
    }


}
