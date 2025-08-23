package com.to_do.To_Do.repository;

import com.to_do.To_Do.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Integer> {
}
