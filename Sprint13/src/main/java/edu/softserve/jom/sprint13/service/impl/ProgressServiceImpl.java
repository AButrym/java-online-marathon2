package edu.softserve.jom.sprint13.service.impl;

import edu.softserve.jom.sprint13.entity.Progress;
import edu.softserve.jom.sprint13.entity.Progress.TaskStatus;
import edu.softserve.jom.sprint13.entity.Task;
import edu.softserve.jom.sprint13.entity.User;
import edu.softserve.jom.sprint13.exception.ProgressNotFoundException;
import edu.softserve.jom.sprint13.exception.TaskNotFoundException;
import edu.softserve.jom.sprint13.repository.ProgressRepository;
import edu.softserve.jom.sprint13.service.ProgressService;
import org.apache.logging.log4j.CloseableThreadContext;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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
        return Progress.builder()
                .started(now).updated(now)
                .task(task).trainee(user)
                .status(TaskStatus.PENDING)
                .build();
    }

    @Override
    public Progress addOrUpdateProgress(Progress progress) {
        // TODO: check logic
        return progressRepository.save(progress);
    }

    @Override
    public boolean setStatus(TaskStatus taskStatus, Progress progress) {
        var inBase = progressRepository.findById(progress.getId());
        if (inBase.isPresent()) {
            var progressInBase = inBase.get();
            if (progressInBase.getStatus() != taskStatus) {
                progressInBase.setStatus(taskStatus);
                return true;
            }
        } else {
            progress.setStatus(taskStatus);
            progressRepository.save(progress);
            return true;
        }
        return false;
    }

    @Override
    public List<Progress> allProgressByUserIdAndMarathonId(UUID userId, UUID marathonId) {
        return List.of();
    }

    @Override
    public List<Progress> allProgressByUserIdAndSprintId(UUID userId, UUID sprintId) {
        return List.of();
    }
}
