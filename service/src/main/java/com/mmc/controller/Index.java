package com.mmc.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mmc.model.Task;
import com.mmc.repository.TaskRepository;

@CrossOrigin
@RestController
public class Index {
//	@Autowired
//	TaskRepository taskRepository;
	@GetMapping("/")
	public String index() {
		return "mmc";
	}
//	@PostMapping("/task")
//	public String test(@RequestBody Task task) {
//		taskRepository.save(task);
//		return null;
//	}
}
