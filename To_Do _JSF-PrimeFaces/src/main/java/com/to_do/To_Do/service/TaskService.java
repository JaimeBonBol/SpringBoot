package com.to_do.To_Do.service;

import com.to_do.To_Do.model.Task;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;

import java.time.LocalDate;
import java.util.List;


public interface TaskService {

    List<Task> getTasks();

    Task getTaskById(Integer id);

    // Dependiendo si ecnuentra id o no, guarda o actualiza gracias a JPA.
    void saveTask(Task task);

    void updateTask(Integer id, String newTtitle, String newDescription, Boolean newCompleted, LocalDate newDueDate);

    void deleteTask(Integer id);

    void markTaskAsCompleted(Integer id);

    public List<Task> getPendingTasks();

    public List<Task> getCompletedTasks();

}
