package com.to_do.To_Do.service;

import com.to_do.To_Do.model.Task;
import com.to_do.To_Do.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class TaskServiceImpl implements TaskService{

    @Autowired
    private TaskRepository taskRepository;


    @Override
    public List<Task> getTasks() {
        return taskRepository.findAll();
    }

    @Override
    public Task getTaskById(Integer id) {
        return taskRepository.findById(id).orElse(null);
    }

    @Override
    public void saveTask(Task task) {
        taskRepository.save(task);
    }

    @Override
    public void updateTask(Integer id, String newTtitle, String newDescription, Boolean newCompleted, LocalDate newDueDate) {
        Task taskUpdate = taskRepository.findById(id).orElse(null);

        if (taskUpdate != null){
            taskUpdate.setTitle(newTtitle);
            taskUpdate.setDescription(newDescription);
            taskUpdate.setCompleted(newCompleted);
            taskUpdate.setDueDate(newDueDate);

            taskRepository.save(taskUpdate);
        }
    }

    @Override
    public void deleteTask(Integer id) {
        taskRepository.deleteById(id);
    }

    @Override
    public void markTaskAsCompleted(Integer id) {
        Task taskMark = taskRepository.findById(id).orElse(null);

        if (taskMark != null){
            taskMark.setCompleted(true);
            taskRepository.save(taskMark);
        }
    }

    @Override
    public List<Task> getPendingTasks() {
        return taskRepository.findByCompleted(false);
    }

    @Override
    public List<Task> getCompletedTasks() {
        return taskRepository.findByCompleted(true);
    }
}
