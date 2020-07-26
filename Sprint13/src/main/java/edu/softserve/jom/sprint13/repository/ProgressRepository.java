package edu.softserve.jom.sprint13.repository;

import edu.softserve.jom.sprint13.entity.Progress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProgressRepository extends JpaRepository<Progress, UUID> {
}
