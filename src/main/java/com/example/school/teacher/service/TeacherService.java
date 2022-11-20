package com.example.school.teacher.service;

import com.example.school.myExceptions.AgeValidException;
import com.example.school.myExceptions.NameValidException;
import com.example.school.teacher.dao.model.TeacherDTO;
import com.example.school.teacher.mapper.Mapper;
import com.example.school.teacher.repo.TeacherRepo;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TeacherService {

    private final TeacherRepo teacherRepo;
    private final Mapper mapper;

    public TeacherService(TeacherRepo teacherRepo, Mapper mapper) {
        this.teacherRepo = teacherRepo;
        this.mapper = mapper;
    }

    @Transactional
    public void addTeacher(TeacherDTO teacherDTO){
        teacherValidator(teacherDTO);
    }

    private void teacherValidator(@NotNull TeacherDTO teacherDTO) {

        if (teacherDTO.getName().length() < 3){
            throw new NameValidException();
        }
        if (teacherDTO.getAge() <= 18){
            throw new AgeValidException();
        }
        teacherRepo.save(mapper.teacherDtoToTeacher(teacherDTO));

    }

}
