package com.mmc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;

import com.mmc.model.Task;
import com.mmc.repository.TaskRepository;

@Service
public class TaskService {
  @Autowired
  TaskRepository taskRepository;
  public void create(Task task) {
	  taskRepository.save(task);
  }
  public void delete(Task task) {
	  taskRepository.delete(task);
  }
  public Page<Task> findAll(int page,int size) {
	Pageable pageable = PageRequest.of(page, size, Sort.by(new Order(Direction.DESC, "id")));
    return taskRepository.findAll(pageable);
  }
}