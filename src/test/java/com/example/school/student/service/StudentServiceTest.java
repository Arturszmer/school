package com.example.school.student.service;

import com.example.school.myExceptions.AgeValidException;
import com.example.school.myExceptions.EmailValidException;
import com.example.school.myExceptions.NameValidException;
import com.example.school.student.dao.model.Student;
import com.example.school.student.dao.model.StudentDTO;
import com.example.school.student.repo.StudentRepo;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class StudentServiceTest {

    @Autowired
    StudentRepo studentRepo;
    @Autowired
    StudentService studentService;

    @Test
    public void shouldCreateAStudent(){
        // given
        StudentDTO studentDTO = studentDTO();

        // when
        studentService.addStudent(studentDTO);
        List<Student> allStudents = studentRepo.findAll();

        // then
        assertThat(allStudents.size()).isEqualTo(1);
    }

    @Test
    public void shouldThrowANameLengthException() {
        // given
        StudentDTO studentDTO = new StudentDTO("A",
                "Brzęczyszczykiewicz",
                22, "teacher@gmail.com",
                "polski",
                new ArrayList<>());

        // expect
        assertThrows(NameValidException.class, () -> studentService.addStudent(studentDTO));
    }

    @Test
    public void shouldThrowAnAgeException() {
        // given
        StudentDTO studentDTO = new StudentDTO("Mariusz",
                "Brzęczyszczykiewicz",
                17, "teacher@gmail.com",
                "polski",
                new ArrayList<>());

        // expect
        assertThrows(AgeValidException.class, () -> studentService.addStudent(studentDTO));
    }

    @Test
    public void shouldThrowAnEmailExceptionWithoutUncorrectDomain() {
        // given
        StudentDTO studentDTO = new StudentDTO("Mariusz",
                "Brzęczyszczykiewicz",
                22, "teacher@gmail",
                "Przedsiębiorczość i Finanse",
                 new ArrayList<>());

        // expect
        assertThrows(EmailValidException.class, () -> studentService.addStudent(studentDTO));
    }

    @Test
    public void shouldThrowAnEmailExceptionWithoutMonkey() {
        // given
        StudentDTO studentDTO = new StudentDTO("Mariusz",
                "Brzęczyszczykiewicz",
                22, "teachergmail.pl",
                "Przedsiębiorczość i Finanse",
                 new ArrayList<>());

        // expect
        assertThrows(EmailValidException.class, () -> studentService.addStudent(studentDTO));
    }

    @Test
    public void shouldThrowAnEmailExceptionWithoutBeforeMonkeyPart() {
        // given
        StudentDTO studentDTO = new StudentDTO("Mariusz",
                "Brzęczyszczykiewicz",
                22, "@gmail.pl",
                "Przedsiębiorczość i Finanse",
                new ArrayList<>());

        // expect
        assertThrows(EmailValidException.class, () -> studentService.addStudent(studentDTO));
    }

    @Test
    public void shouldDeleteStudent(){
        // given
        StudentDTO studentDTO = studentDTO();
        studentService.addStudent(studentDTO);

        // when
        studentService.deleteStudent(studentDTO);
        List<Student> allStudents = studentRepo.findAll();

        // then
        assertTrue(allStudents.isEmpty());

    }

    @Test
    public void shouldUpdateStudentData(){
        // given
        StudentDTO studentDTO = studentDTO();
        studentService.addStudent(studentDTO);

        // when
        studentService.updateStudent(studentDTO.getUuid(), new StudentDTO("Andrzej",
                "Kowalski",
                33, "username@domain.com",
                "Przedsiębiorczość i Finanse",
                new ArrayList<>()));
        Optional<Student> updatedStudent = studentRepo.findByUuid(studentDTO.getUuid());

        // then
        assertThat(updatedStudent.get().getName()).isEqualTo("Andrzej");
    }

    @Test
    public void shoudThrowMailValidExceptionInEditStudentData(){
        // given
        StudentDTO studentDTO = studentDTO();
        studentService.addStudent(studentDTO);

        // expect
        assertThrows(EmailValidException.class, () -> studentService.updateStudent(studentDTO.getUuid(), new StudentDTO("Andrzej",
                "Kowalski",
                33, "username@com",
                "Przedsiębiorczość i Finanse",
                new ArrayList<>())));
    }

    @NotNull
    private static StudentDTO studentDTO() {
        return new StudentDTO("Mariusz",
                "Brzęczyszczykiewicz",
                19, "username@domain.com",
                "Przedsiębiorczość i Finanse",
                new ArrayList<>());
    }
}