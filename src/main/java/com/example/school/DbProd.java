package com.example.school;

import com.example.school.student.dao.model.StudentDTO;
import com.example.school.student.repo.StudentRepo;
import com.example.school.student.service.StudentService;
import com.example.school.teacher.dao.model.TeacherDTO;
import com.example.school.teacher.repo.TeacherRepo;
import com.example.school.teacher.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("prod")
public class DbProd implements CommandLineRunner {

    @Autowired
    TeacherRepo teacherRepo;
    @Autowired
    TeacherService teacherService;
    @Autowired
    StudentRepo studentRepo;
    @Autowired
    StudentService studentService;

    @Override
    public void run(String... args) throws Exception {
        TeacherDTO teacherKowalski = new TeacherDTO(
                "Jan", "Kowalski", 52, "kowalskijan@szkola.com", "Ekonomia");
        TeacherDTO teacherNowak = new TeacherDTO(
                "Andrzej", "Nowak", 48, "nowakandrzej@szkola.com", "Finanse");
        TeacherDTO teacherMickiewicz = new TeacherDTO(
                "Jan", "Mickiewicz", 35, "mickiewiczjan@szkola.com", "Pełna księgowość");
        TeacherDTO teacherBrzeczyszczykiewicz = new TeacherDTO(
                "Mariusz", "Brzęczyszczykiewicz", 62,
                "brzeczyszczykiewicz@szkola.com", "Przedsiębiorczość");
        teacherService.addTeacher(teacherKowalski);
        teacherService.addTeacher(teacherNowak);
        teacherService.addTeacher(teacherMickiewicz);
        teacherService.addTeacher(teacherBrzeczyszczykiewicz);

        StudentDTO studentBanas = new StudentDTO(
                "Arkadiusz", "Banaś", 23, "banas@gmail.com", "Księgowość");
        StudentDTO studentKowal = new StudentDTO(
                "Mirosław", "Kowal", 23, "kowal@gmail.com", "Księgowość");
        StudentDTO studentBeczka = new StudentDTO(
                "Michał", "Beczka", 23, "beczka@gmail.com", "Przedsiębiorczość i finanse");
        StudentDTO studentGolden = new StudentDTO(
                "Łukasz", "Golden", 23, "golden@gmail.com", "Przedsiębiorczość i finanse");
        studentService.addStudent(studentBanas);
        studentService.addStudent(studentKowal);
        studentService.addStudent(studentBeczka);
        studentService.addStudent(studentGolden);

        teacherService.assignExistingStudent(teacherMickiewicz.getUuid(), studentBanas.getUuid());
        teacherService.assignExistingStudent(teacherMickiewicz.getUuid(), studentKowal.getUuid());
        teacherService.assignExistingStudent(teacherKowalski.getUuid(), studentGolden.getUuid());
        teacherService.assignExistingStudent(teacherKowalski.getUuid(), studentBeczka.getUuid());
        teacherService.assignExistingStudent(teacherKowalski.getUuid(), studentKowal.getUuid());
        teacherService.assignExistingStudent(teacherKowalski.getUuid(), studentBanas.getUuid());

        studentService.assignExistingTeacher(studentBeczka.getUuid(), teacherBrzeczyszczykiewicz.getUuid());
        studentService.assignExistingTeacher(studentGolden.getUuid(), teacherNowak.getUuid());
        studentService.assignExistingTeacher(studentBeczka.getUuid(), teacherKowalski.getUuid());

    }
}
