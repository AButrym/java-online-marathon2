package com.softserve.edu.repository;

import com.softserve.edu.model.Progress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProgressRepository extends JpaRepository<Progress, Long> {
    List<Progress> findAllByTraineeIdAndTaskSprintMarathonId(Long userId, Long marathonId);
    List<Progress> findAllByTraineeIdAndTaskSprintId(Long userId, Long sprintId);
}