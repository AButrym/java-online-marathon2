package com.softserve.edu.dto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MentorStudent {
    private final String mentortName;
    private final List<String> studentNames;

    public MentorStudent(String mentortName, List<String> studentNames) {
        this.mentortName = mentortName;
        this.studentNames = new ArrayList<>(studentNames);
    }

    public String getMentortName() {
        return mentortName;
    }

    public List<String> getStudentNames() {
        return Collections.unmodifiableList(studentNames);
    }
}
