package com.mmc.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.mmc.model.Task;

public interface TaskRepository extends JpaRepository<Task,Integer> {
  
}