package com.mmc.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.mmc.model.TaskType;

public interface TaskTypeRepository extends JpaRepository<TaskType,Integer> {
  
}