package com.softserve.edu.service.impl;

import com.softserve.edu.model.Marathon;
import com.softserve.edu.model.Sprint;
import com.softserve.edu.exception.MarathonNotFoundException;
import com.softserve.edu.exception.SprintNotFoundException;
import com.softserve.edu.repository.MarathonRepository;
import com.softserve.edu.repository.SprintRepository;
import com.softserve.edu.service.SprintService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class SprintServiceImpl implements SprintService {
    private final SprintRepository sprintRepository;
    private final MarathonRepository marathonRepository;

    @Override
    public List<Sprint> getSprintsByMarathonId(Long marathonId) {
        return new ArrayList<>( // defensive copy
                marathonRepository.findById(marathonId)
                        .orElseThrow(() -> new MarathonNotFoundException(marathonId))
                        .getSprints());
    }

    @Override
    public boolean addSprintToMarathon(Sprint sprint, Marathon marathon) {
        if (sprint.getMarathon() != null) {
            // reassigning a sprint to another marathon is not allowed
            return false;
        }
        var marathonId = marathon.getId();
        marathon = marathonRepository.findById(marathonId)
                .orElseThrow(() -> new MarathonNotFoundException(marathonId));
        sprint.setMarathon(marathon);
        sprint.setId(sprintRepository.save(sprint).getId());
        marathon.getSprints().add(sprint);
        marathonRepository.save(marathon);
        return true;
    }

    @Override
    public boolean updateSprint(Sprint sprint) {
        // persist a sprint by calling addSprintToMarathon()
        if (sprint.getId() == null
                || sprint.getMarathon() == null
                || !sprint.getMarathon().getSprints().contains(sprint)) {
            return false;
        }
        sprintRepository.save(sprint);
        return true;
    }

    @Override
    public Sprint getSprintById(Long sprintId) {
        return sprintRepository.findById(sprintId)
                .orElseThrow(() -> new SprintNotFoundException(sprintId));
    }
}
