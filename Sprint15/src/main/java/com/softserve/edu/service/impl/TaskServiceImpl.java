package com.softserve.edu.service.impl;

import com.softserve.edu.model.Sprint;
import com.softserve.edu.model.Task;
import com.softserve.edu.exception.SprintNotFoundException;
import com.softserve.edu.exception.TaskNotFoundException;
import com.softserve.edu.repository.SprintRepository;
import com.softserve.edu.repository.TaskRepository;
import com.softserve.edu.service.TaskService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final SprintRepository sprintRepository;

    @Transactional
    @Override
    public Task addTaskToSprint(Task task, Sprint sprint) {
        if (task.getSprint() != null) {
            throw new IllegalStateException(
                    "Reassigning a task to another sprint is not allowed");
        }
        var sprintId = sprint.getId();
        sprint = sprintRepository.findById(sprintId)
                .orElseThrow(() -> new SprintNotFoundException(sprintId));
        task.setSprint(sprint);
        task = taskRepository.save(task);
        sprint.getTasks().add(task);
        sprintRepository.save(sprint);
        return task;
    }

    @Transactional(readOnly = true)
    @Override
    public Task getTaskById(Long taskId) {
        return taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException(taskId));
    }
}
