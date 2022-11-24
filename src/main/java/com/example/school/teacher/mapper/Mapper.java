package com.example.school.teacher.mapper;

import com.example.school.student.dao.model.Student;
import com.example.school.student.dao.model.StudentDTO;
import com.example.school.teacher.dao.model.Teacher;
import com.example.school.teacher.dao.model.TeacherDTO;
import org.springframework.stereotype.Component;

@Component
public class Mapper {

    public TeacherDTO teacherToDTO(Teacher teacher){
        return new TeacherDTO(teacher.getName(),
                teacher.getLastName(),
                teacher.getAge(),
                teacher.getEmail(),
                teacher.getSubject(),
                teacher.getStudents().stream().map(this::studentToDTO).toList());
    }

    public Teacher teacherDtoToTeacher(TeacherDTO teacherDTO){
        Teacher teacher = new Teacher();
        teacher.setName(teacherDTO.getName());
        teacher.setLastName(teacherDTO.getLastName());
        teacher.setAge(teacherDTO.getAge());
        teacher.setEmail(teacherDTO.getEmail());
        teacher.setSubject(teacherDTO.getSubject());
        teacher.setUuid(teacherDTO.getUuid());
        for (Student student: teacherDTO.getStudentDTOS().stream().map(this::studentDtoToStudent).toList()) {
            teacher.assignStudent(student);
        }
        return teacher;
    }

    public StudentDTO studentToDTO(Student student){
        return new StudentDTO(student.getName(),
                student.getLastName(),
                student.getAge(),
                student.getEmail(),
                student.getFieldOfStudy(),
                student.getTeachers().stream().map(this::teacherToDTO).toList());
    }

    public Student studentDtoToStudent (StudentDTO studentDTO){
        Student student = new Student();
        student.setName(studentDTO.getName());
        student.setLastName(studentDTO.getLastName());
        student.setAge(studentDTO.getAge());
        student.setEmail(studentDTO.getEmail());
        student.setFieldOfStudy(studentDTO.getFieldOfStudy());
        student.setUuid(studentDTO.getUuid());
        studentDTO.getTeacherDTOS().stream().map(this::teacherDtoToTeacher).forEach(student::assignTeacher);
        return student;
    }
}