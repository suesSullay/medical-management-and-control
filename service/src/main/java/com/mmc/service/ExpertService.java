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

import com.mmc.model.Expert;
import com.mmc.model.Expert;
import com.mmc.model.Rule;
import com.mmc.model.User;
import com.mmc.repository.ExpertRepository;

@Service
public class ExpertService {
  @Autowired
  ExpertRepository expertRepository;
  public void create(Expert expert) {
	  expertRepository.save(expert);
  }
  public void delete(Expert expert) {
	  expertRepository.delete(expert);
  }
  public Page<Expert> findPage(User user ,String keyName,int page,int size){
	  Pageable pageable = PageRequest.of(page, size, Sort.by(new Order(Direction.DESC, "id")));
	  Specification<Expert> specification = new Specification<Expert>() {
          @Override
          public Predicate toPredicate(Root<Expert> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
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
	  return expertRepository.findAll(specification, pageable);
  }
}