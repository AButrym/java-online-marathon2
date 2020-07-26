package edu.softserve.jom.sprint13.service.impl;

import edu.softserve.jom.sprint13.entity.Progress;
import edu.softserve.jom.sprint13.entity.Progress.TaskStatus;
import edu.softserve.jom.sprint13.entity.Task;
import edu.softserve.jom.sprint13.entity.User;
import edu.softserve.jom.sprint13.exception.ProgressNotFoundException;
import edu.softserve.jom.sprint13.repository.ProgressRepository;
import edu.softserve.jom.sprint13.service.ProgressService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class ProgressServiceImpl implements ProgressService {
    ProgressRepository progressRepository;

    public ProgressServiceImpl(ProgressRepository progressRepository) {
        this.progressRepository = progressRepository;
    }

    @Override
    public Progress getProgressById(UUID id) {
        return progressRepository.findById(id)
                .orElseThrow(() -> new ProgressNotFoundException(id));
    }

    @Override
    public Progress addTaskForStudent(Task task, User user) {
        Instant now = Instant.now();
        Progress progress = Progress.builder()
                .started(now).updated(now)
                .task(task).trainee(user)
                .status(TaskStatus.PENDING)
                .build();
        return progressRepository.save(progress);
    }

    @Override
    public Progress addOrUpdateProgress(Progress progress) {
        var validator = Validation.buildDefaultValidatorFactory().getValidator();
        var violations = validator.validate(progress);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
        var fromBD = progressRepository.findById(progress.getId());
        if (fromBD.isPresent()) {
            var oldProgress = fromBD.get();
            oldProgress.setStatus(progress.getStatus());
            oldProgress.setStarted(progress.getStarted());
            oldProgress.setUpdated(Instant.now());
            oldProgress.setTask(progress.getTask());
            oldProgress.setTrainee(progress.getTrainee());
            progress = oldProgress;
        }
        return progressRepository.save(progress);
    }

    @Override
    public boolean setStatus(TaskStatus taskStatus, Progress progress) {
        var fromBD = progressRepository.findById(progress.getId());
        if (fromBD.isPresent()) {
            var progressInBase = fromBD.get();
            if (progressInBase.getStatus() == taskStatus) {
                return false;
            }
            progress = progressInBase;
        }
        progress.setStatus(taskStatus);
        progressRepository.save(progress);
        return true;
    }

    @Override
    public List<Progress> allProgressByUserIdAndMarathonId(UUID userId, UUID marathonId) {
        return progressRepository.findAllByTraineeIdAndTaskSprintMarathonId(
                userId, marathonId
        );
    }

    @Override
    public List<Progress> allProgressByUserIdAndSprintId(UUID userId, UUID sprintId) {
        return progressRepository.findAllByTraineeIdAndTaskSprintId(
                userId, sprintId
        );
    }
}
