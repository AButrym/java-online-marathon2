package edu.softserve.jom.sprint13.service.impl;

import edu.softserve.jom.sprint13.entity.Marathon;
import edu.softserve.jom.sprint13.entity.Sprint;
import edu.softserve.jom.sprint13.exception.SprintNotFoundException;
import edu.softserve.jom.sprint13.repository.SprintRepository;
import edu.softserve.jom.sprint13.service.SprintService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class SprintServiceImpl implements SprintService {
    private final SprintRepository sprintRepository;

    public SprintServiceImpl(SprintRepository sprintRepository) {
        this.sprintRepository = sprintRepository;
    }

    @Override
    public List<Sprint> getSprintsByMarathonId(UUID marathonId) {
        return sprintRepository.findSprintsByMarathonId(marathonId);
    }

    @Override
    public boolean addSprintToMarathon(Sprint sprint, Marathon marathon) {
        if (sprint.getMarathon() != marathon) {
            if (sprint.getMarathon() != null) {
                sprint.getMarathon().getSprints().remove(sprint);
            }
            sprint.setMarathon(marathon);
            marathon.getSprints().add(sprint);
            return true;
        }
        return false;
    }

    @Override
    public boolean updateSprint(Sprint sprint) {
        if (sprint.getId() == null) {
            return false;
        }
        var stored = sprintRepository.findById(sprint.getId());
        if (stored.isEmpty()) {
            return false;
        }
        var found = stored.get();
        found.setMarathon(sprint.getMarathon());
        found.setFinish(sprint.getFinish());
        found.setStartDate(sprint.getStartDate());
        found.setTitle(sprint.getTitle());
        sprintRepository.save(found);
        return true;
    }

    @Override
    public Sprint getSprintById(UUID sprintId) {
        return sprintRepository.findById(sprintId)
                .orElseThrow(() -> new SprintNotFoundException(sprintId));
    }
}
