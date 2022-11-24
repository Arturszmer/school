package com.example.school.student.controller;

import com.example.school.student.dao.model.StudentDTO;
import com.example.school.student.repo.StudentRepo;
import com.example.school.student.service.StudentService;
import com.example.school.teacher.mapper.Mapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;


@SpringBootTest
@Transactional
@AutoConfigureMockMvc
class StudentControllerTest {

    @Autowired
    StudentService studentService;
    @Autowired
    StudentRepo studentRepo;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    Mapper mapper;


    private void setup() {
        StudentDTO studentMariusz = new StudentDTO("Mariusz",
                "Brzęczyszczykiewicz",
                19, "username@domain.com",
                "Przedsiębiorczość i Finanse");
        StudentDTO studentLukasz = new StudentDTO(
                "Łukasz",
                "Golden",
                23, "golden@gmail.com",
                "Przedsiębiorczość i finanse");
        studentService.addStudent(studentMariusz);
        studentService.addStudent(studentLukasz);
    }

}