package edu.softserve.jom.sprint13.service.impl;

import edu.softserve.jom.sprint13.entity.Marathon;
import edu.softserve.jom.sprint13.exception.MarathonNotFoundException;
import edu.softserve.jom.sprint13.repository.MarathonRepository;
import edu.softserve.jom.sprint13.service.MarathonService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;


@Service
@Transactional
public class MarathonServiceImpl implements MarathonService {
    private final MarathonRepository marathonRepository;

    public MarathonServiceImpl(MarathonRepository marathonRepository) {
        this.marathonRepository = marathonRepository;
    }

    @Override
    public List<Marathon> getAll() {
        return marathonRepository.findAll();
    }

    @Override
    public Marathon getMarathonById(UUID id) {
        return marathonRepository.findById(id).orElseThrow(
                () -> new MarathonNotFoundException(id)
        );
    }

    @Override
    public Marathon createOrUpdate(Marathon marathon) {
        return marathonRepository.save(marathon);
    }

    @Override
    public void deleteMarathonById(UUID id) {
        marathonRepository.deleteById(id);
    }
}
