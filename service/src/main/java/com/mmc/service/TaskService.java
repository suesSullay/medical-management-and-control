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
  public Page<Task> findAll(String name,int tid,String state,String user,  int page,int size) {
	  Pageable pageable = PageRequest.of(page, size, Sort.by(new Order(Direction.DESC, "id")));
	  Specification<Task> specification = new Specification<Task>() {
          @Override
          public Predicate toPredicate(Root<Task> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
              Predicate predicate = cb.conjunction();
              if(!"".equals(name)) {
            	  predicate.getExpressions().add(cb.like(root.get("name"), "%"+name+"%"));
              }
              if(tid!=-1) {
            	  predicate.getExpressions().add(cb.equal(root.get("taskType").get("id"), tid));
              }
              if("未完成".equals(state)) {
            	  predicate.getExpressions().add(cb.notEqual(root.get("endNum"), root.get("userNum")));
              }else if("完成".equals(state)) {
            	  predicate.getExpressions().add(cb.equal(root.get("endNum"), root.get("userNum")));
              }
              if(!"全部".equals(user)) {
            	  predicate.getExpressions().add(cb.like(root.get("users"),"%"+",\"name\":\""+user+"\","+"%"));
              }
              return predicate;
          }
      };
	  return taskRepository.findAll(specification, pageable);
  }
}