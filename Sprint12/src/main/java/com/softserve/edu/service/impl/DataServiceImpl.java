package com.softserve.edu.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.softserve.edu.entity.Communication;
import com.softserve.edu.entity.Entity;
import com.softserve.edu.entity.Solution;
import com.softserve.edu.service.DataService;
import org.springframework.stereotype.Service;

@Service
public class DataServiceImpl implements DataService {
    private List<Entity> students = new ArrayList<>();
    private List<Entity> mentors = new ArrayList<>();
    private List<Entity> sprints = new ArrayList<>();
    private List<Communication> communications = new ArrayList<>();
    private List<Solution> solutions = new ArrayList<>();

    private Optional<Integer> getIdByName(String name, List<Entity> storage) {
        return storage.stream()
                .filter(e -> name.equals(e.getName()))
                .findFirst()
                .map(Entity::getId);
    }

    public void addStudent(String studentName) {
        students.add(Entity.of(studentName));
    }

    public void addMentor(String mentorName) {
        mentors.add(Entity.of(mentorName));
    }

    public void addSprint(String sprintName) {
        sprints.add(Entity.of(sprintName));
    }

    public void addCommunication(String studentName, String mentorName) {
        int studentId = getIdByName(studentName, students)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Unknown student name: " + studentName));
        int mentorId = getIdByName(mentorName, mentors)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Unknown mentor name: " + mentorName));
        communications.add(new Communication(studentId, mentorId));
    }

    public void addSolution(String studentName, String sprintName, int score) {
        int studentId = getIdByName(studentName, students)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Unknown student name: " + studentName));
        int sprintId = getIdByName(sprintName, sprints)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Unknown sprint name: " + sprintName));
        solutions.add(new Solution(studentId, sprintId, score));
    }

    // getters

    public List<Entity> getStudents() {
        return Collections.unmodifiableList(students);
    }

    public List<Entity> getMentors() {
        return Collections.unmodifiableList(mentors);
    }

    public List<Entity> getSprints() {
        return Collections.unmodifiableList(sprints);
    }

    public List<Communication> getCommunications() {
        return Collections.unmodifiableList(communications);
    }

    public List<Solution> getSolutions() {
        return Collections.unmodifiableList(solutions);
    }
}
