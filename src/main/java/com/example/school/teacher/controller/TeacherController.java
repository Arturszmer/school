package com.example.school.teacher.controller;

import com.example.school.student.dao.model.StudentDTO;
import com.example.school.teacher.dao.model.TeacherDTO;
import com.example.school.teacher.service.TeacherService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/teachers")
public class TeacherController {

    private final TeacherService teacherService;

    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @GetMapping
    @ResponseBody
    public List<TeacherDTO> getAllTeachers(){
        return teacherService.allTeachers();
    }

    @GetMapping("/{teacherLastName}")
    @ResponseBody
    public List<StudentDTO> getAllStudentsFromTeacher(@PathVariable String teacherLastName){
        return teacherService.allStudentsFromTeacher(teacherLastName);
    }

    @GetMapping("/sort")
    @ResponseBody
    public List<TeacherDTO> getAllTeachersSortedByLastName() {
        return teacherService.allTeachersSortedByLastName();
    }

    @GetMapping("/{name}/{lastName}")
    @ResponseBody
    public TeacherDTO getTeacherByNameAndLastName(@PathVariable String name, @PathVariable String lastName){
        return teacherService.techerByNameAndLastName(name, lastName);
    }
}