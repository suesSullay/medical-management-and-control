package com.mmc.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.mmc.model.Expert;

public interface ExpertRepository extends JpaRepository<Expert,Integer> , JpaSpecificationExecutor<Expert>{
  
}