package edu.softserve.jom.sprint13.service;

import edu.softserve.jom.sprint13.entity.Progress;
import edu.softserve.jom.sprint13.entity.Progress.TaskStatus;
import edu.softserve.jom.sprint13.entity.Task;
import edu.softserve.jom.sprint13.entity.User;

import java.util.List;
import java.util.UUID;

public interface ProgressService {

    Progress getProgressById(UUID id);

    Progress addTaskForStudent(Task task, User user);

    Progress addOrUpdateProgress(Progress progress);

    boolean setStatus(TaskStatus taskStatus, Progress progress);

    List<Progress> allProgressByUserIdAndMarathonId(UUID userId, UUID marathonId);

    List<Progress> allProgressByUserIdAndSprintId(UUID userId, UUID sprintId);
}