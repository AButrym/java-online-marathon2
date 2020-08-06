package com.softserve.edu.service;

import com.softserve.edu.model.Progress;
import com.softserve.edu.model.Progress.TaskStatus;
import com.softserve.edu.model.Task;
import com.softserve.edu.model.User;

import java.util.List;

public interface ProgressService {

    Progress getProgressById(Long id);

    Progress addTaskForStudent(Task task, User user);

    Progress addOrUpdateProgress(Progress progress);

    boolean setStatus(TaskStatus taskStatus, Progress progress);

    List<Progress> allProgressByUserIdAndMarathonId(Long userId, Long marathonId);

    List<Progress> allProgressByUserIdAndSprintId(Long userId, Long sprintId);
}