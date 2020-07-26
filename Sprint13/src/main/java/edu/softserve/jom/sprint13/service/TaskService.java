package edu.softserve.jom.sprint13.service;

import edu.softserve.jom.sprint13.entity.Sprint;
import edu.softserve.jom.sprint13.entity.Task;

import java.util.UUID;

public interface TaskService {

    Task addTaskToSprint(Task task, Sprint sprint);

    Task getTaskById(UUID task);

}
