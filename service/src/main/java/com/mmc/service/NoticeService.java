package com.mmc.service;

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
import com.mmc.model.Notice;
import com.mmc.model.Rule;
import com.mmc.model.User;
import com.mmc.repository.NoticeRepository;

@Service
public class NoticeService {
  @Autowired
  NoticeRepository NoticeRepository;
  public void create(Notice notice) {
	  NoticeRepository.save(notice);
  }
  public void delete(Notice notice) {
	  NoticeRepository.delete(notice);
  }
  public Page<Notice> findPage(User user ,String keyName,int page,int size){
	  Pageable pageable = PageRequest.of(page, size, Sort.by(new Order(Direction.DESC, "id")));
	  Specification<Notice> specification = new Specification<Notice>() {
          @Override
          public Predicate toPredicate(Root<Notice> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
              Predicate predicate = cb.conjunction();
              if(user.getRule()!=Rule.ROOT) {
            	  predicate.getExpressions().add(cb.equal(root.get("user").get("id"), user.getId()));
              }
              if(!"".equals(keyName)) {
            	  predicate.getExpressions().add(cb.like(root.get("name"), "%"+keyName+"%"));
              }
              return predicate;
          }
      };
	  return NoticeRepository.findAll(specification, pageable);
  }
}