package edu.softserve.jom.sprint13.repository;

import edu.softserve.jom.sprint13.entity.Progress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProgressRepository extends JpaRepository<Progress, UUID> {
    List<Progress> findAllByTraineeIdAndTaskSprintMarathonId(
            UUID userId, UUID marathonId
    );

    List<Progress> findAllByTraineeIdAndTaskSprintId(
            UUID userId, UUID sprintId
    );
}
