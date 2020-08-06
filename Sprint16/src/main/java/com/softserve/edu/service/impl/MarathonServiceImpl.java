package com.softserve.edu.service.impl;

import com.softserve.edu.model.Marathon;
import com.softserve.edu.exception.MarathonNotFoundException;
import com.softserve.edu.repository.MarathonRepository;
import com.softserve.edu.service.MarathonService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@AllArgsConstructor
public class MarathonServiceImpl implements MarathonService {
    private final MarathonRepository marathonRepository;

    @Transactional(readOnly = true)
    @Override
    public List<Marathon> getAll() {
        return marathonRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Marathon getMarathonById(Long id) {
        return marathonRepository.findById(id).orElseThrow(
                () -> new MarathonNotFoundException(id)
        );
    }

    @Transactional
    @Override
    public Marathon createOrUpdate(Marathon marathon) {
        return marathonRepository.save(marathon);
    }

    @Transactional
    @Override
    public void deleteMarathonById(Long id) {
        marathonRepository.deleteById(id);
    }

}
