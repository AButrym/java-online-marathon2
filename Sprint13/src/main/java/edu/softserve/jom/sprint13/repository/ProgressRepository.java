package edu.softserve.jom.sprint13.repository;

import edu.softserve.jom.sprint13.entity.Progress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProgressRepository extends JpaRepository<Progress, UUID> {
    //    @Query("select p from Progress p where p.trainee.id = :userID and p.task.sprint.marathon.id = :marathonId")
    List<Progress> findAllByTraineeIdAndTaskSprintMarathonId(
            /*@Param("userID")*/ UUID userId,
            /*@Param("marathonId")*/ UUID marathonId);

    //    @Query("select p from Progress p where p.trainee.id = :userID and p.task.sprint.id = :sprintId")
    List<Progress> findAllByTraineeIdAndTaskSprintId(
            /*@Param("userID")*/ UUID userId,
            /*@Param("sprintId")*/ UUID sprintId);

    //    @Query("select p from Progress p where p.trainee.id = :userID and p.task.id = :taskId")
    Optional<Progress> findByTraineeIdAndTaskId(
            /*@Param("userID")*/ UUID userId,
            /*@Param("taskId")*/ UUID taskId);
}
