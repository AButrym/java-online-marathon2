package edu.softserve.jom.sprint13.repository;

import edu.softserve.jom.sprint13.entity.Sprint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SprintRepository extends JpaRepository<Sprint, UUID> {
    List<Sprint> findSprintsByMarathonId(UUID marathonId);
}
