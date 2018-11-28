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

import com.mmc.model.CallBoard;
import com.mmc.repository.CallBoardRepository;

@Service
public class CallBoardService {
  @Autowired
  CallBoardRepository CallBoardRepository;
  public void create(CallBoard callBoard) {
	  CallBoardRepository.save(callBoard);
  }
  public void delete(CallBoard callBoard) {
	  CallBoardRepository.delete(callBoard);
  }
  public Page<CallBoard> findPage(String keyName,int page,int size){
	  Pageable pageable = PageRequest.of(page, size, Sort.by(new Order(Direction.DESC, "id")));
	  Specification<CallBoard> specification = new Specification<CallBoard>() {
          @Override
          public Predicate toPredicate(Root<CallBoard> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
              Predicate predicate = cb.conjunction();
              if(!"".equals(keyName)) {
            	  predicate.getExpressions().add(cb.like(root.get("name"), "%"+keyName+"%"));
              }
              return predicate;
          }
      };
	  return CallBoardRepository.findAll(specification, pageable);
  }
}