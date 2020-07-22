package com.softserve.edu.service;

import com.softserve.edu.entity.Communication;
import com.softserve.edu.entity.Entity;
import com.softserve.edu.entity.Solution;

import java.util.List;

public interface DataService {
    void addStudent(String studentName);
    void addMentor(String mentorName);
    void addSprint(String sprintName);
    void addCommunication(String studentName, String mentorName);
    void addSolution(String studentName, String sprintName, int score);
    List<Entity> getStudents();
    List<Entity> getMentors();
    List<Entity> getSprints();
    List<Communication> getCommunications();
    List<Solution> getSolutions();
}
