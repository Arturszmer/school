package com.example.school.student.service;

import com.example.school.student.dao.model.Student;
import com.example.school.student.dao.model.StudentDTO;
import com.example.school.student.repo.StudentRepo;
import com.example.school.teacher.dao.model.TeacherDTO;
import com.example.school.teacher.mapper.Mapper;
import com.example.school.teacher.repo.TeacherRepo;
import com.example.school.validators.TeacherAndStudentValidator;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StudentService {

    private final StudentRepo studentRepo;
    private final Mapper mapper;
    private final TeacherAndStudentValidator validator;
    private final TeacherRepo teacherRepo;

    public StudentService(StudentRepo studentRepo, Mapper mapper, TeacherAndStudentValidator validator, TeacherRepo teacherRepo) {
        this.studentRepo = studentRepo;
        this.mapper = mapper;
        this.validator = validator;
        this.teacherRepo = teacherRepo;
    }

    @Transactional(readOnly = true)
    public List<StudentDTO> allStudents() {
        return studentRepo.findAll().stream()
                .map(mapper::studentToDTO)
                .toList();
    }

    @Transactional
    public void addStudent(StudentDTO studentDTO){
        studentValidator(studentDTO);
        studentRepo.save(mapper.studentDtoToStudent(studentDTO));
    }

    @Transactional
    public void deleteStudent(StudentDTO studentDTO){
        Student studentToDelete = studentRepo.findByUuid(studentDTO.getUuid()).orElseThrow();
        studentRepo.delete(studentToDelete);
    }

    @Transactional
    public void updateStudent(String changedStudentUUID, StudentDTO studentDTO){
        studentValidator(studentDTO);
        Student studentToUpdate = studentRepo.findByUuid(changedStudentUUID).orElseThrow();
        studentToUpdate.setName(studentDTO.getName());
        studentToUpdate.setLastName(studentDTO.getLastName());
        studentToUpdate.setAge(studentDTO.getAge());
        studentToUpdate.setEmail(studentDTO.getEmail());
        studentToUpdate.setFieldOfStudy(studentDTO.getFieldOfStudy());
        studentRepo.save(studentToUpdate);
    }

    @Transactional
    public void assignExistingTeacher(String studentUUID, String teacherUUID){
        Student student = studentRepo.findByUuid(studentUUID).orElseThrow();
        student.assignTeacher(teacherRepo.findByUuid(teacherUUID).orElseThrow());
    }

    @Transactional
    public void assignNewTeacher(String studentUUID, TeacherDTO teacherDTO){
        Student student = studentRepo.findByUuid(studentUUID).orElseThrow();
        teacherValidator(teacherDTO);
        student.assignTeacher(mapper.teacherDtoToTeacher(teacherDTO));
    }

    @Transactional
    public void removeTeacher(String studentUUID, String teacherUUID){
        Student student = studentRepo.findByUuid(studentUUID).orElseThrow();
        student.removeTeacher(student.getTeachers().stream()
                .filter(teacher -> teacher.getUuid().equals(teacherUUID))
                .findFirst().orElseThrow());
    }

    private void studentValidator(StudentDTO studentDTO) {
        validator.nameLengthValid(studentDTO.getName());
        validator.ageValid(studentDTO.getAge());
        validator.emailValid(studentDTO.getEmail());
    }

    private void teacherValidator(TeacherDTO teacherDTO) {
        validator.nameLengthValid(teacherDTO.getName());
        validator.ageValid(teacherDTO.getAge());
        validator.emailValid(teacherDTO.getEmail());
    }

    public List<TeacherDTO> allTeachersFromStudent(String studentLastName) {
        Student student = studentRepo.findByLastName(studentLastName).orElseThrow();
        return student.getTeachers().stream()
                .map(mapper::teacherToDTO)
                .toList();
    }

    public List<StudentDTO> allStudentsSortedByLastName() {
        List<Student> students = studentRepo.findAll(Sort.by(Sort.Direction.ASC, "lastName"));
        return students.stream()
                .map(mapper::studentToDTO)
                .toList();
    }
}
