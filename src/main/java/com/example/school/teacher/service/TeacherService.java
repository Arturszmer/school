package com.example.school.teacher.service;

import com.example.school.teacher.dao.model.TeacherDTO;
import com.example.school.teacher.mapper.Mapper;
import com.example.school.teacher.repo.TeacherRepo;
import com.example.school.validators.TeacherAndStudentValidator;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TeacherService {

    private final TeacherRepo teacherRepo;
    private final Mapper mapper;
    private final TeacherAndStudentValidator validator;

    public TeacherService(TeacherRepo teacherRepo, Mapper mapper, TeacherAndStudentValidator validator) {
        this.teacherRepo = teacherRepo;
        this.mapper = mapper;
        this.validator = validator;
    }

    @Transactional
    public void addTeacher(TeacherDTO teacherDTO){
        teacherValidator(teacherDTO);
    }

    private void teacherValidator(@NotNull TeacherDTO teacherDTO) {

        validator.nameLengthValid(teacherDTO.getName());
        validator.ageValid(teacherDTO.getAge());
        validator.emailValid(teacherDTO.getEmail());
        teacherRepo.save(mapper.teacherDtoToTeacher(teacherDTO));
    }
}
