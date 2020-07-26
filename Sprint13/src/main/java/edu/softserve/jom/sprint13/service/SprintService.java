package edu.softserve.jom.sprint13.service;

import edu.softserve.jom.sprint13.entity.Marathon;
import edu.softserve.jom.sprint13.entity.Sprint;

import java.util.List;
import java.util.UUID;

public interface SprintService {

    List<Sprint> getSprintsByMarathonId(UUID sprintId);

    boolean addSprintToMarathon(Sprint sprint, Marathon marathon);

    boolean updateSprint(Sprint sprint);

    Sprint getSprintById(UUID sprintId);

}
