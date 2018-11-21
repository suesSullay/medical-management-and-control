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

import com.mmc.model.Message;
import com.mmc.model.Msg;
import com.mmc.model.Task;
import com.mmc.service.MessageService;
import com.mmc.service.TaskService;

/**
 * userController
 */
@CrossOrigin
@RestController
@RequestMapping("/message")
public class MessageController {
  @Autowired
  MessageService messageService;
  @PostMapping("/")
  public Msg create(@RequestBody Message message) {
	  messageService.create(message);
	  return Msg.success();
  }
}