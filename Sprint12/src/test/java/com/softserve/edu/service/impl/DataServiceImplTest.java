package com.softserve.edu.service.impl;

import com.softserve.edu.entity.Entity;
import com.softserve.edu.service.DataService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DataServiceImplTest {
    static DataService dataService;

    static String student1 = "Oleksandr";
    static String student2 = "Taras";
    static String mentor1 = "Mykola";
    static String mentor2 = "Nataliia";
    static String mentor3 = "Yaroslav";
    static String sprint1 = "Sprint01";
    static String sprint2 = "Sprint02";

    @BeforeAll
    static void beforeAll() {
        dataService = new DataServiceImpl();
    }

    @AfterEach
    void tearDown() {
        dataService.clearAll();
    }

    @Test
    void addStudent() {
        dataService.addStudent(student1);
        dataService.addStudent(student2);

        assertEquals(2,
                dataService.getStudents().size());
    }

    @Test
    void addMentor() {
        dataService.addMentor(mentor1);
        dataService.addMentor(mentor2);
        dataService.addMentor(mentor3);

        assertEquals(3,
                dataService.getMentors().size());
    }

    @Test
    void addSprint() {
        dataService.addSprint(sprint1);
        dataService.addSprint(sprint2);

        assertEquals(2,
                dataService.getSprints().size());
    }

    @Test
    void addCommunication() {
        dataService.addStudent(student1);
        dataService.addStudent(student2);

        dataService.addMentor(mentor1);

        dataService.addCommunication(student1, mentor1);
        dataService.addCommunication(student2, mentor1);

        assertEquals(2,
                dataService.getCommunications().size());
    }

    @Test
    void addSolution() {
        dataService.addStudent(student1);
        dataService.addSprint(sprint1);
        dataService.addSprint(sprint2);

        dataService.addSolution(student1, sprint1, 100);
        dataService.addSolution(student1, sprint2, 100);

        assertEquals(2,
                dataService.getSolutions().size());
    }

    @Test
    void getStudents() {
        dataService.addStudent(student1);
        dataService.addStudent(student2);

        assertEquals(Set.of(student1, student2),
                dataService.getStudents().stream()
                        .map(Entity::getName)
                        .collect(Collectors.toSet()));
    }

    @Test
    void getMentors() {
        dataService.addMentor(mentor1);
        dataService.addMentor(mentor2);
        dataService.addMentor(mentor3);

        assertEquals(Set.of(mentor1, mentor2, mentor3),
                dataService.getMentors().stream()
                        .map(Entity::getName)
                        .collect(Collectors.toSet()));
    }

    @Test
    void getSprints() {
        dataService.addSprint(sprint1);
        dataService.addSprint(sprint2);

        assertEquals(Set.of(sprint1, sprint2),
                dataService.getSprints().stream()
                        .map(Entity::getName)
                        .collect(Collectors.toSet()));
    }
}