package com.example.school.teacher.repo;

import com.example.school.teacher.dao.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TeacherRepo extends JpaRepository<Teacher, Long> {

    Optional<Teacher> findByUuid(String uuid);
    Optional<Teacher> findByLastName(String lastName);
}
