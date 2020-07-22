package com.softserve.edu.dto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StudentScore {
    private final String studentName;
    private final List<SprintScore> sprintScore;

    public StudentScore(String studentName, List<SprintScore> sprintScore) {
        this.studentName = studentName;
        this.sprintScore = new ArrayList<>(sprintScore);
    }

    public String getStudentName() {
        return studentName;
    }

    public List<SprintScore> getSprintScore() {
        return Collections.unmodifiableList(sprintScore);
    }
}
