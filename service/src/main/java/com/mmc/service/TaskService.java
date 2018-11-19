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
  public Page<Task> findAll(String name,int tid,String state, int page,int size) {
	  Pageable pageable = PageRequest.of(page, size, Sort.by(new Order(Direction.DESC, "id")));
	  if("全部".equals(state)&&tid>0) {
		  return taskRepository.findAll(name, tid,pageable);
	  }else if("全部".equals(state)&&tid==-1) {
		  return taskRepository.findAll(name,pageable);
	  }else if("未完成".equals(state)&&tid>0) {
		  return taskRepository.findAllNOEnd(name, tid, pageable);
	  }else if("未完成".equals(state)&&tid==-1) {
		  return taskRepository.findAllNOEnd(name, tid, pageable);
	  }else if("完成".equals(state)&&tid>0) {
		  return taskRepository.findAllEnd(name, tid, pageable);
	  }else if("完成".equals(state)&&tid==-1) {
		  return taskRepository.findAllEnd(name, pageable);
	  }else {
		  return taskRepository.findAll(name, tid,pageable);
	  }
  }
}