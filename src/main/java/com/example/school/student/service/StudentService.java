package com.example.school.student.service;

import com.example.school.student.dao.model.StudentDTO;
import com.example.school.student.repo.StudentRepo;
import com.example.school.teacher.mapper.Mapper;
import com.example.school.validators.TeacherAndStudentValidator;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

    private final StudentRepo studentRepo;
    private final Mapper mapper;
    private final TeacherAndStudentValidator validator;

    public StudentService(StudentRepo studentRepo, Mapper mapper, TeacherAndStudentValidator validator) {
        this.studentRepo = studentRepo;
        this.mapper = mapper;
        this.validator = validator;
    }

    public void addStudent(StudentDTO studentDTO){
        addStudentValidator(studentDTO);
    }

    private void addStudentValidator(StudentDTO studentDTO) {
        validator.nameLengthValid(studentDTO.getName());
        validator.ageValid(studentDTO.getAge());
        validator.emailValid(studentDTO.getEmail());
        studentRepo.save(mapper.studentDtoToStudent(studentDTO));
    }
}
