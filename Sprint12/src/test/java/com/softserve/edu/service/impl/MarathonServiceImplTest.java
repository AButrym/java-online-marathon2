package com.softserve.edu.service.impl;

import com.softserve.edu.dto.AverageScore;
import com.softserve.edu.dto.MentorStudent;
import com.softserve.edu.dto.SprintScore;
import com.softserve.edu.dto.StudentScore;
import com.softserve.edu.service.DataService;
import com.softserve.edu.service.MarathonService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;

import static java.util.stream.Collectors.toSet;
import static org.junit.jupiter.api.Assertions.assertEquals;

class MarathonServiceImplTest {
    static DataService dataService;
    static MarathonService marathonService;

    static List<String> students = List.of("Oleksandr", "Taras", "Petro");
    static List<String> mentors = List.of("Mykola", "Nataliia", "Yaroslav");
    static List<String> sprints = List.of("Sprint01", "Sprint02", "Sprint03");
    static List<List<Integer>> scores = List.of(
            List.of(100, 95, 90),
            List.of(90, 92, 100),
            List.of(100, 80, 90)
    );
    static List<Double> avgScores = List.of(95.0, 94.0, 90.0);

    @BeforeAll
    static void beforeAll() {
        dataService = new DataServiceImpl();
        marathonService = new MarathonServiceImpl(dataService);

        students.forEach(s -> dataService.addStudent(s));
        mentors.forEach(m -> dataService.addMentor(m));
        sprints.forEach(s -> dataService.addSprint(s));

        for (int i = 0; i < students.size(); i++) {
            String student = students.get(i);
            var it = scores.get(i).iterator();
            sprints.forEach(s -> dataService.addSolution(student, s, it.next()));
        }

        String mentor1 = mentors.get(0);
        students.forEach(s -> dataService.addCommunication(s, mentor1));
    }

    @Test
    void getStudents() {
        assertEquals(
                new HashSet<>(students),
                new HashSet<>(marathonService.getStudents())
        );
    }

    @Test
    void getMentors() {
        assertEquals(
                new HashSet<>(mentors),
                new HashSet<>(marathonService.getMentors())
        );
    }

    @Test
    void studentResult() {
        int iStudent = 0;
        String student1 = students.get(iStudent);
        var studentResult = marathonService.studentResult(student1);
        assertEquals(
                student1,
                studentResult.getStudentName()
        );
        assertEquals(
                sprints.size(),
                studentResult.getSprintScore().size()
        );
        assertEquals(
                new HashSet<>(sprints),
                studentResult.getSprintScore().stream()
                        .map(SprintScore::getSprintName)
                        .collect(toSet())
        );
        assertEquals(
                new HashSet<>(scores.get(iStudent)),
                studentResult.getSprintScore().stream()
                        .map(SprintScore::getScore)
                        .collect(toSet())
        );
    }

    @Test
    void allStudentsResult() {
        List<StudentScore> results = marathonService.allStudentsResult();
        assertEquals(
                new HashSet<>(students),
                results.stream().map(StudentScore::getStudentName)
                        .collect(toSet())
        );
        assertEquals(
                new HashSet<>(sprints),
                results.stream().map(StudentScore::getSprintScore)
                        .flatMap(List::stream)
                        .map(SprintScore::getSprintName)
                        .collect(toSet())
        );
        assertEquals(
                scores.stream().flatMap(List::stream).collect(toSet()),
                results.stream().map(StudentScore::getSprintScore)
                        .flatMap(List::stream)
                        .map(SprintScore::getScore)
                        .collect(toSet())
        );
    }

    @Test
    void studentAverage() {
        for (int iStudent = 0; iStudent < students.size(); iStudent++) {
            String student1 = students.get(iStudent);
            var studentResult = marathonService.studentAverage(student1);
            assertEquals(
                    student1,
                    studentResult.getStudentName()
            );
            assertEquals(
                    avgScores.get(iStudent),
                    studentResult.getAvgScore()
            );
        }
    }

    @Test
    void allStudentsAverage() {
        List<AverageScore> result = marathonService.allStudentsAverage();
        assertEquals(
                new HashSet<>(students),
                result.stream().map(AverageScore::getStudentName)
                        .collect(toSet())
        );
        assertEquals(
                new HashSet<>(avgScores),
                result.stream().map(AverageScore::getAvgScore)
                        .collect(toSet())
        );
    }

    @Test
    void mentorStudents() {
        String mentor1 = mentors.get(0);
        MentorStudent result = marathonService.mentorStudents(mentor1);
        assertEquals(
                mentor1,
                result.getMentortName()
        );
        assertEquals(
                new HashSet<>(students),
                new HashSet<>(result.getStudentNames())
        );
    }
}