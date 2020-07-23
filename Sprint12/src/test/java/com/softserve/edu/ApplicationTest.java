package com.softserve.edu;

import com.softserve.edu.service.DataService;
import com.softserve.edu.service.MarathonService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class ApplicationTest {
    // integration test. Tests beans autowiring
    // beans
    private MarathonService marathonService;
    private DataService dataService;
    // test data
    private static List<String> students = List.of("Oleksandr", "Taras", "Petro");
    private static List<String> mentors = List.of("Mykola", "Nataliia", "Yaroslav");
    private static List<String> sprints = List.of("Sprint01", "Sprint02", "Sprint03");
    private static List<List<Integer>> scores = List.of(
            List.of(100, 95, 90),
            List.of(90, 92, 100),
            List.of(100, 80, 90)
    );
    private static List<Double> avgScores = List.of(95.0, 94.0, 90.0);
    
    @Autowired
    public ApplicationTest(
            MarathonService marathonService,
            DataService dataService
    ){
        this.marathonService = marathonService;
        this.dataService = dataService;
        initData();
    }

    private void initData() {
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
    void checkGetStudents() {
        assertEquals(
                new HashSet<>(students),
                new HashSet<>(marathonService.getStudents())
        );
    }

}
