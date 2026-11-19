package com.example.studentskillmanagement.repository;

import com.example.studentskillmanagement.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, String> {

}