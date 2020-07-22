package com.softserve.edu.dto;

public class AverageScore {
    private final String studentName;
    private final double avgScore;

    public AverageScore(String studentName, double avgScore) {
        this.studentName = studentName;
        this.avgScore = avgScore;
    }

    public String getStudentName() {
        return studentName;
    }

    public double getAvgScore() {
        return avgScore;
    }
}
