
package com.example.studentskillmanagement.service;

import com.example.studentskillmanagement.model.Student;
import com.example.studentskillmanagement.model.StudentPerformance;
import com.example.studentskillmanagement.repository.StudentRepository;

import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    // Add Student
    public Student addStudent(Student student) {
        if (studentRepository.existsById(student.getStudentId())) {
            throw new RuntimeException("Student ID already exists!");
        }

        validateScores(student.getProgrammingScore(),
                student.getMathematicsScore());

        return studentRepository.save(student);
    }

    // Get All Students
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    // Get Student By ID
    public Student getStudentById(String id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found!"));
    }

    // Search By Email
    public Student searchByEmail(String email) {
        return studentRepository.findAll()
                .stream()
                .filter(s -> s.getEmail().equalsIgnoreCase(email))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Student not found!"));
    }

    // Search By Course
    public List<Student> searchByCourse(String course) {
        return studentRepository.findAll()
                .stream()
                .filter(s -> s.getCourseName().equalsIgnoreCase(course))
                .collect(Collectors.toList());
    }

    // Update Student Scores and Skills
    public Student updateStudent(String id,
                                 double programmingScore,
                                 double mathematicsScore,
                                 List<String> technicalSkills) {

        validateScores(programmingScore, mathematicsScore);

        Student student = getStudentById(id);

        student.setProgrammingScore(programmingScore);
        student.setMathematicsScore(mathematicsScore);
        student.setTechnicalSkills(technicalSkills);

        return studentRepository.save(student);
    }

    // Delete Student
    public void deleteStudent(String id) {
        if (!studentRepository.existsById(id)) {
            throw new RuntimeException("Student not found!");
        }

        studentRepository.deleteById(id);
    }

    // Calculate Average
    public double calculateAverage(Student student) {
        return (student.getProgrammingScore()
                + student.getMathematicsScore()) / 2.0;
    }

    // Performance Category
    public String getPerformanceCategory(Student student) {
        double average = calculateAverage(student);

        if (average >= 85)
            return "Excellent";
        else if (average >= 70)
            return "Good";
        else if (average >= 50)
            return "Satisfactory";
        else
            return "Needs Improvement";
    }

    // Stronger Subject
    public String getStrongerSubject(Student student) {
        if (student.getProgrammingScore()
                > student.getMathematicsScore()) {
            return "Programming";
        }
        else if (student.getMathematicsScore()
                > student.getProgrammingScore()) {
            return "Mathematics";
        }
        else {
            return "Both are Equal";
        }
    }

    // Merit List
    public List<Student> getMeritList() {
        return studentRepository.findAll()
                .stream()
                .sorted((s1, s2) ->
                        Double.compare(calculateAverage(s2),
                                calculateAverage(s1)))
                .collect(Collectors.toList());
    }

    // Students With Same Skills
    public List<List<Student>> getStudentsWithSameSkills() {
        Map<Set<String>, List<Student>> groups =
                studentRepository.findAll()
                        .stream()
                        .collect(Collectors.groupingBy(
                                s -> new HashSet<>(s.getTechnicalSkills())
                        ));

        return groups.values()
                .stream()
                .filter(group -> group.size() > 1)
                .collect(Collectors.toList());
    }

    // Programming Below 50 and Mathematics >= 75
    public List<Student> getWeakProgrammingStudents() {
        return studentRepository.findAll()
                .stream()
                .filter(s -> s.getProgrammingScore() < 50)
                .filter(s -> s.getMathematicsScore() >= 75)
                .collect(Collectors.toList());
    }

    // Student Performance Details
    public StudentPerformance getStudentPerformance(String id) {
        Student student = getStudentById(id);

        double average = calculateAverage(student);
        String category = getPerformanceCategory(student);
        String strongerSubject = getStrongerSubject(student);

        return new StudentPerformance(
                student.getStudentId(),
                student.getStudentName(),
                average,
                category,
                strongerSubject
        );
    }

    // Validate Scores
    private void validateScores(double programming, double mathematics) {
        if (programming < 0 || programming > 100
                || mathematics < 0 || mathematics > 100) {
            throw new RuntimeException(
                    "Scores must be between 0 and 100!");
        }
    }
}
