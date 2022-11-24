package com.example.school.student.service;

import com.example.school.myExceptions.AgeValidException;
import com.example.school.myExceptions.EmailValidException;
import com.example.school.myExceptions.NameValidException;
import com.example.school.student.dao.model.Student;
import com.example.school.student.dao.model.StudentDTO;
import com.example.school.student.repo.StudentRepo;
import com.example.school.teacher.dao.model.Teacher;
import com.example.school.teacher.dao.model.TeacherDTO;
import com.example.school.teacher.repo.TeacherRepo;
import com.example.school.teacher.service.TeacherService;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

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
    @Autowired
    TeacherRepo teacherRepo;
    @Autowired
    TeacherService teacherService;

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
                "polski");

        // expect
        assertThrows(NameValidException.class, () -> studentService.addStudent(studentDTO));
    }

    @Test
    public void shouldThrowAnAgeException() {
        // given
        StudentDTO studentDTO = new StudentDTO("Mariusz",
                "Brzęczyszczykiewicz",
                17, "teacher@gmail.com",
                "polski");

        // expect
        assertThrows(AgeValidException.class, () -> studentService.addStudent(studentDTO));
    }

    @Test
    public void shouldThrowAnEmailExceptionWithoutUncorrectDomain() {
        // given
        StudentDTO studentDTO = new StudentDTO("Mariusz",
                "Brzęczyszczykiewicz",
                22, "teacher@gmail",
                "Przedsiębiorczość i Finanse");

        // expect
        assertThrows(EmailValidException.class, () -> studentService.addStudent(studentDTO));
    }

    @Test
    public void shouldThrowAnEmailExceptionWithoutMonkey() {
        // given
        StudentDTO studentDTO = new StudentDTO("Mariusz",
                "Brzęczyszczykiewicz",
                22, "teachergmail.pl",
                "Przedsiębiorczość i Finanse");

        // expect
        assertThrows(EmailValidException.class, () -> studentService.addStudent(studentDTO));
    }

    @Test
    public void shouldThrowAnEmailExceptionWithoutBeforeMonkeyPart() {
        // given
        StudentDTO studentDTO = new StudentDTO("Mariusz",
                "Brzęczyszczykiewicz",
                22, "@gmail.pl",
                "Przedsiębiorczość i Finanse");

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
                "Przedsiębiorczość i Finanse"));
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
                "Przedsiębiorczość i Finanse")));
    }

    @Test
    public void shouldAssignExistingTeacher() {
        // given
        StudentDTO studentDTO = studentDTO();
        studentService.addStudent(studentDTO);
        TeacherDTO teacherDTO = new TeacherDTO("Artur",
                "Brzęczyszczykiewicz",
                22, "username@domain.com",
                "polski");
        teacherService.addTeacher(teacherDTO);

        // when
        studentService.assignExistingTeacher(studentDTO.getUuid(), teacherDTO.getUuid());
        Student studentWithTeacher = studentRepo.findByUuid(studentDTO.getUuid()).orElseThrow();

        // then
        assertThat(studentWithTeacher.getTeachers().size()).isEqualTo(1);
    }

    @Test
    public void shouldAssignNewTeacherToStudent() {
        // given
        StudentDTO studentDTO = studentDTO();
        studentService.addStudent(studentDTO);

        // when
        studentService.assignNewTeacher(studentDTO.getUuid(), new TeacherDTO("Artur",
                "Brzęczyszczykiewicz",
                22, "username@domain.com",
                "polski"));

        // then
        assertThat(studentRepo.findByUuid(studentDTO.getUuid()).orElseThrow().getTeachers().size()).isEqualTo(1);
    }

    @Test
    public void shouldRemoveTeacherFromStudent() {
        // given
        StudentDTO studentDTO = studentDTOWithTeachers();
        Student student = studentRepo.findByUuid(studentDTO.getUuid()).orElseThrow();
        Teacher teacherToRemove = student.getTeachers().stream().findFirst().orElseThrow();

        // when
        studentService.removeTeacher(student.getUuid(), teacherToRemove.getUuid());

        // then
        assertThat(student.getTeachers().size()).isEqualTo(0);
    }

    @Test
    public void shouldFindStudentByNameAndLastName() {
        // given
        StudentDTO studentDTO = studentDTO();
        studentService.addStudent(studentDTO);

        // when
        StudentDTO studentMariusz = studentService.studentByNameAndLastName("Mariusz", "Brzęczyszczykiewicz");

        // then
        assertThat(studentMariusz.getLastName()).isEqualTo("Brzęczyszczykiewicz");
    }

    @NotNull
    private StudentDTO studentDTO() {
        return new StudentDTO("Mariusz",
                "Brzęczyszczykiewicz",
                19, "username@domain.com",
                "Przedsiębiorczość i Finanse");
    }

    @NotNull
    private StudentDTO studentDTOWithTeachers() {
        StudentDTO studentDTO = new StudentDTO("Mariusz",
                "Brzęczyszczykiewicz",
                19, "username@domain.com",
                "Przedsiębiorczość i Finanse");
        studentService.addStudent(studentDTO);
        studentService.assignNewTeacher(studentDTO.getUuid(), new TeacherDTO("Dariusz",
                "Miłosz",
                42, "username@domain.com",
                "Ekonomia"));
        return studentDTO;
    }
}