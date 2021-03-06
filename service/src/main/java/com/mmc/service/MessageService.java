package com.mmc.service;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.mmc.model.Message;
import com.mmc.model.MessageType;
import com.mmc.model.Task;
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
  public Page<Message> findPage(MessageType messageType ,int page,int size){
	  Pageable pageable = PageRequest.of(page, size, Sort.by(new Order(Direction.DESC, "id")));
	  Specification<Message> specification = new Specification<Message>() {
          @Override
          public Predicate toPredicate(Root<Message> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
              Predicate predicate = cb.conjunction();
              if(messageType!=null) {
            	  predicate.getExpressions().add(cb.equal(root.get("messageType"), messageType));
              }
              return predicate;
          }
      };
	  return messageRepository.findAll(specification, pageable);
  }
}