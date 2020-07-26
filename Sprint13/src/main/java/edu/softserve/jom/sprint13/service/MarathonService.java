package edu.softserve.jom.sprint13.service;

import edu.softserve.jom.sprint13.entity.Marathon;

import java.util.List;
import java.util.UUID;

public interface MarathonService {

    List<Marathon> getAll();

    Marathon getMarathonById(UUID marathonId);

    Marathon createOrUpdate(Marathon marathon);

    void deleteMarathonById(UUID marathonId);
}
