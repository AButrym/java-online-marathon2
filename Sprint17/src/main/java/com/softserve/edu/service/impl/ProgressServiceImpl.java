package com.softserve.edu.service.impl;

import com.softserve.edu.model.Progress;
import com.softserve.edu.model.Progress.TaskStatus;
import com.softserve.edu.model.Task;
import com.softserve.edu.model.User;
import com.softserve.edu.exception.ProgressNotFoundException;
import com.softserve.edu.repository.ProgressRepository;
import com.softserve.edu.service.ProgressService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class ProgressServiceImpl implements ProgressService {
    private final ProgressRepository progressRepository;

    @Override
    public Progress getProgressById(Long id) {
        return progressRepository.findById(id)
                .orElseThrow(() -> new ProgressNotFoundException(id));
    }

    @Transactional
    @Override
    public Progress addTaskForStudent(Task task, User user) {
        Instant now = Instant.now();
        Progress progress = Progress.builder()
                .started(now).updated(now)
                .task(task).trainee(user)
                .status(TaskStatus.NEW)
                .build();
        return progressRepository.save(progress);
    }

    @Transactional
    @Override
    public Progress addOrUpdateProgress(Progress progress) {
        var fromDB = progressRepository.findById(progress.getId());
        if (fromDB.isPresent()) {
            var oldProgress = fromDB.get();
            oldProgress.setStatus(progress.getStatus());
            oldProgress.setStarted(progress.getStarted());
            oldProgress.setTask(progress.getTask());
            oldProgress.setTrainee(progress.getTrainee());
            progress = oldProgress;
        }
        progress.setUpdated(Instant.now());
        return progressRepository.save(progress);
    }

    @Transactional
    @Override
    public boolean setStatus(TaskStatus taskStatus, Progress progress) {
        var fromDB = progressRepository.findById(progress.getId());
        if (fromDB.isPresent()) {
            var progressInBase = fromDB.get();
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
    public List<Progress> allProgressByUserIdAndMarathonId(Long userId, Long marathonId) {
        return progressRepository.findAllByTraineeIdAndTaskSprintMarathonId(
                userId, marathonId
        );
    }

    @Override
    public List<Progress> allProgressByUserIdAndSprintId(Long userId, Long sprintId) {
        return progressRepository.findAllByTraineeIdAndTaskSprintId(
                userId, sprintId
        );
    }
}
