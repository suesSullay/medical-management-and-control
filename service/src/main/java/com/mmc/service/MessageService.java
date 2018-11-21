package com.mmc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mmc.model.Message;
import com.mmc.model.TaskType;
import com.mmc.repository.MessageRepository;
import com.mmc.repository.TaskTypeRepository;

@Service
public class MessageService {
  @Autowired
  MessageRepository messageRepository;
  public void create(Message message) {
	  messageRepository.save(message);
  }
}