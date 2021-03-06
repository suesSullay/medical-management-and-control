package com.mmc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
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
  public Msg findALL(@RequestParam(value="name",defaultValue="")String name, @RequestParam(value="tid",defaultValue="-1") int tid,@RequestParam(value="state",defaultValue="全部")String state,@RequestParam(value="user",defaultValue="全部")String user,@RequestParam(value="page",defaultValue="0") int page,@RequestParam(value="size",defaultValue="7") int size) {
	Page<Task> taskPage = taskService.findAll(name, tid, state, user, page, size);	
	return Msg.success().add("taskPage", taskPage);
  }
  @PostMapping("/")
  public Msg create(@RequestBody Task task) {
	  taskService.create(task);
	  return Msg.success();
  }
  @DeleteMapping("/")
  public Msg delete(@RequestBody Task task) {
	  taskService.delete(task);
	  return Msg.success();
  }
  @GetMapping("/")
  public Msg findById(@RequestParam("id") int id) {
	return Msg.success().add("task", taskService.findById(id));
  }
}