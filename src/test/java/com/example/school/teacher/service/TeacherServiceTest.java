package com.example.school.teacher.service;

import com.example.school.myExceptions.AgeValidException;
import com.example.school.myExceptions.EmailValidException;
import com.example.school.myExceptions.NameValidException;
import com.example.school.student.dao.model.Student;
import com.example.school.student.dao.model.StudentDTO;
import com.example.school.student.repo.StudentRepo;
import com.example.school.student.service.StudentService;
import com.example.school.teacher.dao.model.Teacher;
import com.example.school.teacher.dao.model.TeacherDTO;
import com.example.school.teacher.repo.TeacherRepo;
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
class TeacherServiceTest {

    @Autowired
    TeacherRepo teacherRepo;
    @Autowired
    TeacherService teacherService;
    @Autowired
    StudentRepo studentRepo;
    @Autowired
    StudentService studentService;

    @Test
    public void shouldCreateATeacher(){
        // given
        TeacherDTO teacherDTO = getTeacherDTO();

        // when
        teacherService.addTeacher(teacherDTO);
        List<Teacher> all = teacherRepo.findAll();

        // then
        assertThat(all.size()).isEqualTo(1);
    }

    @Test
    public void shouldShowAllTeachers(){
        // given
        TeacherDTO teacherDTO = getTeacherDTO();
        teacherService.addTeacher(teacherDTO);

        // then
        assertThat(teacherService.allTeachers().size()).isEqualTo(1);
    }

    @Test
    public void shouldThrowANameLengthException() {
        // given
        TeacherDTO teacherDTO = new TeacherDTO("A",
                "Brzęczyszczykiewicz",
                22, "teacher@gmail.com",
                "polski");

        // expect
        assertThrows(NameValidException.class, () -> teacherService.addTeacher(teacherDTO));
    }

    @Test
    public void shouldThrowAnAgeException() {
        // given
        TeacherDTO teacherDTO = new TeacherDTO("Artur",
                "Brzęczyszczykiewicz",
                17, "teacher@gmail.com",
                "polski");

        // expect
        assertThrows(AgeValidException.class, () -> teacherService.addTeacher(teacherDTO));
    }

    @Test
    public void shouldThrowAnEmailExceptionWithoutUncorrectDomain() {
        // given
        TeacherDTO teacherDTO = new TeacherDTO("Artur",
                "Brzęczyszczykiewicz",
                22, "teacher@gmail",
                "polski");

        // expect
        assertThrows(EmailValidException.class, () -> teacherService.addTeacher(teacherDTO));
    }

    @Test
    public void shouldThrowAnEmailExceptionWithoutMonkey() {
        // given
        TeacherDTO teacherDTO = new TeacherDTO("Artur",
                "Brzęczyszczykiewicz",
                22, "teachergmail.pl",
                "polski");

        // expect
        assertThrows(EmailValidException.class, () -> teacherService.addTeacher(teacherDTO));
    }

    @Test
    public void shouldThrowAnEmailExceptionWithoutBeforeMonkeyPart() {
        // given
        TeacherDTO teacherDTO = new TeacherDTO("Artur",
                "Brzęczyszczykiewicz",
                22, "@gmail.pl",
                "polski");

        // expect
        assertThrows(EmailValidException.class, () -> teacherService.addTeacher(teacherDTO));
    }

    @Test
    public void shouldDeleteTeacher() {
        // given
        TeacherDTO teacherDTO = getTeacherDTO();
        teacherService.addTeacher(teacherDTO);

        // when
        teacherService.deleteTeacher(teacherDTO);
        List<Teacher> allTeachers = teacherRepo.findAll();

        // then
        assertThat(allTeachers.size()).isEqualTo(0);
    }

    @Test
    public void shouldEditTeacherAllData() {
        // given
        TeacherDTO teacherDTO = getTeacherDTO();
        teacherService.addTeacher(teacherDTO);

        // when
        teacherService.updateTeacher(teacherDTO.getUuid(), new TeacherDTO("Mariusz",
                "Nowak",
                28,
                "autograf@doom.com",
                "Angelsi"));
        Optional<Teacher> updatedTeacher = teacherRepo.findByUuid(teacherDTO.getUuid());
        // then
        assertThat(updatedTeacher.get().getName()).isEqualTo("Mariusz");
    }

    @Test
    public void shouldThrowAgeValidExceptionInEditTeacherData() {
        // given
        TeacherDTO teacherDTO = getTeacherDTO();
        teacherService.addTeacher(teacherDTO);

        // expect
        assertThrows(AgeValidException.class, () -> teacherService.updateTeacher(teacherDTO.getUuid(), new TeacherDTO("Mariusz",
                "Nowak",
                17,
                "autograf@doom.com",
                "Angelsi")));
    }

    @Test
    public void shouldAssignExistingStudentToTeacher() {
        // given
        TeacherDTO teacherDTO = getTeacherDTO();
        teacherService.addTeacher(teacherDTO);
        StudentDTO studentDTO = new StudentDTO("Adam", "Markiewicz",
                20, "adamman@domain.com", "Ekonomia");
        studentService.addStudent(studentDTO);

        // when
        teacherService.assignExistingStudent(teacherDTO.getUuid(), studentDTO.getUuid());
        Teacher teacherWithStudent = teacherRepo.findByUuid(teacherDTO.getUuid()).orElseThrow();

        // then
        assertThat(teacherWithStudent.getStudents().size()).isEqualTo(1);
    }

    @Test
    public void shouldAssignNewStudentToTeacher(){
        // given
        TeacherDTO teacherDTO = getTeacherDTO();
        teacherService.addTeacher(teacherDTO);

        // when
        teacherService.assignNewStudent(teacherDTO.getUuid(), new StudentDTO("Mariusz",
                "Brzęczyszczykiewicz",
                19, "username@domain.com",
                "Przedsiębiorczość i Finanse"));

        System.out.println("Techer:" + teacherRepo.findByUuid(teacherDTO.getUuid()));

        // then
        assertThat(teacherRepo.findByUuid(teacherDTO.getUuid()).get().getStudents().size()).isEqualTo(1);
    }

    @Test
    public void shouldRemoveStudentFromTeacher(){
        // given
        TeacherDTO teacherDTO = getTeacherDTOWithStudents();
        Teacher teacher = teacherRepo.findByUuid(teacherDTO.getUuid()).orElseThrow();
        Student studentToRemove = teacher.getStudents().stream().findFirst().orElseThrow();

        // when
        teacherService.removeStudent(teacher.getUuid(), studentToRemove.getUuid());

        // then
        assertThat(teacher.getStudents().size()).isEqualTo(0);
    }

    @NotNull
    private TeacherDTO getTeacherDTO() {
        return new TeacherDTO("Artur",
                "Brzęczyszczykiewicz",
                22, "username@domain.com",
                "polski");
    }
    @NotNull
    private TeacherDTO getTeacherDTOWithStudents() {
        TeacherDTO teacherDTO = new TeacherDTO("Artur",
                "Brzęczyszczykiewicz",
                22, "username@domain.com",
                "polski");
        teacherService.addTeacher(teacherDTO);
        teacherService.assignNewStudent(teacherDTO.getUuid(), new StudentDTO("Mariusz",
                "Brzęczyszczykiewicz",
                19, "username@domain.com",
                "Przedsiębiorczość i Finanse"));
        return teacherDTO;
    }
}