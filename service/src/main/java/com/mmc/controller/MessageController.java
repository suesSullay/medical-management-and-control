package com.mmc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mmc.model.Message;
import com.mmc.model.MessageType;
import com.mmc.model.Msg;
import com.mmc.service.MessageService;

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
  @GetMapping("/all")
  public Msg findPage(@RequestParam(value="messageType",required=false)MessageType messageType,@RequestParam(value="page",defaultValue="0") int page,@RequestParam(value="size",defaultValue="7") int size) {
	   Page<Message> messagePage = messageService.findPage(messageType, page, size);
	   return Msg.success().add("messagePage", messagePage);
  }
}