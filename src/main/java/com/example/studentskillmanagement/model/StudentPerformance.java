package com.example.studentskillmanagement.model;

public class StudentPerformance {

    private String studentId;
    private String studentName;
    private double average;
    private String performanceCategory;
    private String strongerSubject;

    public StudentPerformance(String studentId, String studentName,
                              double average, String performanceCategory,
                              String strongerSubject) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.average = average;
        this.performanceCategory = performanceCategory;
        this.strongerSubject = strongerSubject;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public double getAverage() {
        return average;
    }

    public String getPerformanceCategory() {
        return performanceCategory;
    }

    public String getStrongerSubject() {
        return strongerSubject;
    }
}