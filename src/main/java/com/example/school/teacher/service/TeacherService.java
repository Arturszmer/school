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
    private final TeacherAndStudentValidator teacherAndStudentValidator;

    public TeacherService(TeacherRepo teacherRepo, Mapper mapper, TeacherAndStudentValidator teacherAndStudentValidator) {
        this.teacherRepo = teacherRepo;
        this.mapper = mapper;
        this.teacherAndStudentValidator = teacherAndStudentValidator;
    }

    @Transactional
    public void addTeacher(TeacherDTO teacherDTO){
        teacherValidator(teacherDTO);
    }

    private void teacherValidator(@NotNull TeacherDTO teacherDTO) {

        teacherAndStudentValidator.nameLengthValid(teacherDTO.getName());
        teacherAndStudentValidator.ageValid(teacherDTO.getAge());
        teacherAndStudentValidator.emailValid(teacherDTO.getEmail());
        teacherRepo.save(mapper.teacherDtoToTeacher(teacherDTO));
    }
}
