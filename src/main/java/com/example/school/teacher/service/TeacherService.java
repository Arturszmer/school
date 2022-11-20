package com.example.school.teacher.service;

import com.example.school.teacher.repo.TeacherRepo;
import org.springframework.stereotype.Service;

@Service
public class TeacherService {

    private final TeacherRepo teacherRepo;

    public TeacherService(TeacherRepo teacherRepo) {
        this.teacherRepo = teacherRepo;
    }


}
