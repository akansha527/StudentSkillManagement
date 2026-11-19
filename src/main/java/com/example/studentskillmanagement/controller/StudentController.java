
package com.example.studentskillmanagement.controller;

import com.example.studentskillmanagement.model.Student;
import com.example.studentskillmanagement.model.StudentPerformance;
import com.example.studentskillmanagement.service.StudentService;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    // Add Student
    @PostMapping
    public Student addStudent(@RequestBody Student student) {
        return studentService.addStudent(student);
    }

    // Get All Students
    @GetMapping
    public List<Student> getAllStudents() {
        return studentService.getAllStudents();
    }

    // Get Student By ID
    @GetMapping("/{id}")
    public Student getStudentById(@PathVariable String id) {
        return studentService.getStudentById(id);
    }

    // Search By Email
    @GetMapping("/search/email")
    public Student searchByEmail(@RequestParam String email) {
        return studentService.searchByEmail(email);
    }

    // Search By Course
    @GetMapping("/search/course")
    public List<Student> searchByCourse(@RequestParam String course) {
        return studentService.searchByCourse(course);
    }

    // Update Student
    @PutMapping("/{id}")
    public Student updateStudent(
            @PathVariable String id,
            @RequestBody Student student) {

        return studentService.updateStudent(
                id,
                student.getProgrammingScore(),
                student.getMathematicsScore(),
                student.getTechnicalSkills()
        );
    }

    // Delete Student
    @DeleteMapping("/{id}")
    public String deleteStudent(@PathVariable String id) {
        studentService.deleteStudent(id);
        return "Student deleted successfully!";
    }

    // Merit List
    @GetMapping("/merit")
    public List<Student> getMeritList() {
        return studentService.getMeritList();
    }

    // Students With Same Skills
    @GetMapping("/same-skills")
    public List<List<Student>> getStudentsWithSameSkills() {
        return studentService.getStudentsWithSameSkills();
    }

    // Weak Programming Students
    @GetMapping("/weak-programming")
    public List<Student> getWeakProgrammingStudents() {
        return studentService.getWeakProgrammingStudents();
    }

    // Student Performance Details
    @GetMapping("/{id}/performance")
    public StudentPerformance getStudentPerformance(@PathVariable String id) {
        return studentService.getStudentPerformance(id);
    }
}
