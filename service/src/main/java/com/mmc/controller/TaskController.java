package com.mmc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mmc.model.Msg;
import com.mmc.model.Task;
import com.mmc.service.TaskService;

/**
 * userController
 */
@CrossOrigin
@RestController
@RequestMapping("/task")
public class TaskController {
  @Autowired
  TaskService taskService;
  @GetMapping("/all")
  public Msg findALL(@RequestParam(value="page",defaultValue="1") int page,@RequestParam(value="page",defaultValue="10m") int size) {
	Page<Task> taskPage = taskService.findAll(page, size);
	return Msg.success().add("taskPage", taskPage);
  }
  @PostMapping("/")
  public Msg create(@RequestBody Task task) {
	  taskService.create(task);
	  return Msg.success();
  }
}