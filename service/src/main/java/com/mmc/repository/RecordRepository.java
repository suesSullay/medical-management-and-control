package com.mmc.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.mmc.model.Record;

public interface RecordRepository extends JpaRepository<Record,Integer> , JpaSpecificationExecutor<Record>{
  
}