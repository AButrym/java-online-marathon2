package com.softserve.edu.service.impl;

import com.softserve.edu.dto.AverageScore;
import com.softserve.edu.dto.MentorStudent;
import com.softserve.edu.dto.SprintScore;
import com.softserve.edu.dto.StudentScore;
import com.softserve.edu.entity.Entity;
import com.softserve.edu.service.DataService;
import com.softserve.edu.service.MarathonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.*;
import static java.util.stream.Collectors.averagingInt;

@Service
public class MarathonServiceImpl implements MarathonService {

    private final DataService dataService;

    @Autowired
    public MarathonServiceImpl(DataService dataService) {
        this.dataService = dataService;
    }

    // inverse indexes

    private Map<Integer, String> ixStudents;
    private Map<Integer, String> ixSprints;
    private Map<Integer, String> ixMentors;

    // lazy initializers

    private void initIxStudents() {
        if (ixStudents == null) {
            ixStudents = dataService.getStudents().stream()
                    .collect(toMap(Entity::getId, Entity::getName));
        }
    }

    private void initIxSprints() {
        if (ixSprints == null) {
            ixSprints = dataService.getSprints().stream()
                    .collect(toMap(Entity::getId, Entity::getName));
        }
    }

    private void initIxMentors() {
        if (ixMentors == null) {
            ixMentors = dataService.getMentors().stream()
                    .collect(toMap(Entity::getId, Entity::getName));
        }
    }

    public List<String> getStudents() {
        return dataService.getStudents().stream()
                .map(Entity::getName)
                .collect(toList());
    }

    public List<String> getMentors() {
        return dataService.getMentors().stream()
                .map(Entity::getName)
                .collect(toList());
    }

    public StudentScore studentResult(String studentName) {
        initIxSprints();
        initIxStudents();

        return new StudentScore(studentName,
                dataService.getSolutions().stream()
                        .filter(e -> ixStudents.get(e.getIdStudent()).equals(studentName))
                        .map(e -> new SprintScore(
                                ixSprints.get(e.getIdSprint()),
                                e.getScore()))
                        .collect(toList())
        );
    }

    public List<StudentScore> allStudentsResult() {
        initIxStudents();
        initIxSprints();

        return dataService.getSolutions().stream()
                .collect(groupingBy(
                        s -> ixStudents.get(s.getIdStudent()),
                        mapping(
                                s -> new SprintScore(
                                        ixSprints.get(s.getIdSprint()),
                                        s.getScore()),
                                toList()
                        )
                )).entrySet().stream()
                .map(me -> new StudentScore(me.getKey(), me.getValue()))
                .collect(toList());
    }

    public AverageScore studentAverage(String studentName) {
        return new AverageScore(
                studentName,
                studentResult(studentName).getSprintScore().stream()
                        .mapToInt(SprintScore::getScore)
                        .average().orElse(0.0)
        );
    }

    public List<AverageScore> allStudentsAverage() {
        return allStudentsResult().stream()
                .collect(groupingBy(StudentScore::getStudentName,
                        flatMapping(x -> x.getSprintScore().stream(),
                                averagingInt(SprintScore::getScore))
                )).entrySet().stream()
                .map(me -> new AverageScore(me.getKey(), me.getValue()))
                .collect(toList());
    }

    public MentorStudent mentorStudents(String mentorName) {
        initIxMentors();
        initIxStudents();
        return new MentorStudent(mentorName,
                dataService.getCommunications().stream()
                        .filter(c -> ixMentors.get(c.getIdMentor()).equals(mentorName))
                        .map(c -> ixStudents.get(c.getIdStudent()))
                        .collect(toList())
        );
    }
}
