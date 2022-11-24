package com.example.school.student.controller;

import com.example.school.student.dao.model.StudentDTO;
import com.example.school.student.service.StudentService;
import com.example.school.teacher.dao.model.TeacherDTO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    @ResponseBody
    public List<StudentDTO> getAllStudents(){
        return studentService.allStudents();
    }

    @GetMapping("/{studentlastName}")
    @ResponseBody
    public List<TeacherDTO> getAllTeacherFromStudent(@PathVariable String studentlastName){
        return studentService.allTeachersFromStudent(studentlastName);
    }

    @GetMapping("/sort")
    public List<StudentDTO> getAllTeachersSortedByLastName(){
        return studentService.allStudentsSortedByLastName();
    }

    @GetMapping("/{name}/{lastName}")
    @ResponseBody
    public StudentDTO getStudentByNameAndLastName(@PathVariable String name, @PathVariable String lastName){
        return studentService.studentByNameAndLastName(name, lastName);
    }
}