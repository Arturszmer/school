package com.example.school.teacher.service;

import com.example.school.student.dao.model.StudentDTO;
import com.example.school.student.repo.StudentRepo;
import com.example.school.teacher.dao.model.Teacher;
import com.example.school.teacher.dao.model.TeacherDTO;
import com.example.school.teacher.mapper.Mapper;
import com.example.school.teacher.repo.TeacherRepo;
import com.example.school.validators.TeacherAndStudentValidator;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TeacherService {

    private final TeacherRepo teacherRepo;
    private final Mapper mapper;
    private final TeacherAndStudentValidator validator;
    private final StudentRepo studentRepo;

    public TeacherService(TeacherRepo teacherRepo, Mapper mapper, TeacherAndStudentValidator validator, StudentRepo studentRepo) {
        this.teacherRepo = teacherRepo;
        this.mapper = mapper;
        this.validator = validator;
        this.studentRepo = studentRepo;
    }

    @Transactional(readOnly = true)
    public List<TeacherDTO> allTeachers(){
        return teacherRepo.findAll().stream()
                .map(mapper::teacherToDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<StudentDTO> allStudentsFromTeacher(String teacherLastName) {
        Teacher teacher = teacherRepo.findByLastName(teacherLastName).orElseThrow();
        return teacher.getStudents().stream().map(mapper::studentToDTO).toList();
    }

    @Transactional
    public void addTeacher(TeacherDTO teacherDTO){
        teacherValidator(teacherDTO);
        teacherRepo.save(mapper.teacherDtoToTeacher(teacherDTO));
    }

    @Transactional
    public void deleteTeacher(TeacherDTO teacherDTO){
        Teacher teacherToDelete = teacherRepo.findByUuid(teacherDTO.getUuid()).orElseThrow();
        teacherRepo.delete(teacherToDelete);
    }

    @Transactional
    public void updateTeacher(String changedTeacherUUID, TeacherDTO editedTeacherDto){
        teacherValidator(editedTeacherDto);
        Teacher teacherToUpdate = teacherRepo.findByUuid(changedTeacherUUID).orElseThrow();
        teacherToUpdate.setName(editedTeacherDto.getName());
        teacherToUpdate.setLastName(editedTeacherDto.getLastName());
        teacherToUpdate.setAge(teacherToUpdate.getAge());
        teacherToUpdate.setEmail(editedTeacherDto.getEmail());
        teacherRepo.save(teacherToUpdate);
    }

    @Transactional
    public void assignExistingStudent(String teacherUUID, String studentUUID){
        Teacher teacher = teacherRepo.findByUuid(teacherUUID).orElseThrow();
        teacher.assignStudent(studentRepo.findByUuid(studentUUID).orElseThrow());
    }

    @Transactional
    public void assignNewStudent(String teacherUUID, StudentDTO studentDTO){
        Teacher teacher = teacherRepo.findByUuid(teacherUUID).orElseThrow();
        studentValidator(studentDTO);
        teacher.assignStudent(mapper.studentDtoToStudent(studentDTO));
    }

    @Transactional
    public void removeStudent(String teacherUUID, String studentUUID){
        Teacher teacher = teacherRepo.findByUuid(teacherUUID).orElseThrow();
        teacher.removeStudent(teacher.getStudents().stream()
                .filter(f -> f.getUuid().equals(studentUUID))
                .findFirst().orElseThrow());
    }
    public List<TeacherDTO> allTeachersSortedByLastName() {
        List<Teacher> sortedTeacher = teacherRepo.findAll(Sort.by(Sort.Direction.ASC, "lastName"));
        return sortedTeacher.stream().map(mapper::teacherToDTO).toList();
    }

    public TeacherDTO techerByNameAndLastName(String name, String lastName){
        return mapper.teacherToDTO(teacherRepo.findByNameAndLastName(name, lastName).orElseThrow());
    }
    private void teacherValidator(@NotNull TeacherDTO teacherDTO) {

        validator.nameLengthValid(teacherDTO.getName());
        validator.ageValid(teacherDTO.getAge());
        validator.emailValid(teacherDTO.getEmail());
    }

    private void studentValidator(@NotNull StudentDTO studentDTO) {

        validator.nameLengthValid(studentDTO.getName());
        validator.ageValid(studentDTO.getAge());
        validator.emailValid(studentDTO.getEmail());
    }
}