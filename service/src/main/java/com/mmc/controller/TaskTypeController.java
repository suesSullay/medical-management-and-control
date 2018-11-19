package com.mmc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mmc.model.Msg;
import com.mmc.model.TaskType;
import com.mmc.service.TaskTypeService;

/**
 * userController
 */
@CrossOrigin
@RestController
@RequestMapping("/taskType")
public class TaskTypeController {
  @Autowired
  TaskTypeService taskTypeService;
  @GetMapping("/all")
  public Msg findALL() {
	List<TaskType> list = taskTypeService.findAll();
	return Msg.success().add("taskTypes", list);
  }
  @PostMapping("/")
  public Msg create(@RequestBody TaskType taskType) {
	  taskTypeService.create(taskType);
	  return Msg.success();
  }
}