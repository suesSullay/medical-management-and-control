package com.mmc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mmc.model.TaskType;
import com.mmc.repository.TaskTypeRepository;

@Service
public class TaskTypeService {
  @Autowired
  TaskTypeRepository taskTypeRepository;
  public void create(TaskType taskType) {
	  taskTypeRepository.save(taskType);
  }
  public void delete(TaskType taskType) {
	  taskTypeRepository.delete(taskType);
  }
  public List<TaskType> findAll() {
    return taskTypeRepository.findAll();
  }
}