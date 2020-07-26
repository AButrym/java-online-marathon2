package edu.softserve.jom.sprint13.service.impl;

import edu.softserve.jom.sprint13.entity.Sprint;
import edu.softserve.jom.sprint13.entity.Task;
import edu.softserve.jom.sprint13.exception.TaskNotFoundException;
import edu.softserve.jom.sprint13.repository.TaskRepository;
import edu.softserve.jom.sprint13.service.TaskService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.UUID;

@Service
@Transactional
public class TaskServiceImpl implements TaskService {
    TaskRepository taskRepository;

    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public Task addTaskToSprint(Task task, Sprint sprint) {
        if (task.getSprint() != sprint) {
            if (task.getSprint() != null) {
                task.getSprint().getTasks().remove(task);
            }
            task.setSprint(sprint);
            sprint.getTasks().add(task);
        }
        return task; // persist by cascade from marathon->sprint->task
    }

    @Override
    public Task getTaskById(UUID taskId) {
        return taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException(taskId));
    }
}
