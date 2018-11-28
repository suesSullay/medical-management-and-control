package com.mmc.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.mmc.model.CallBoard;

public interface CallBoardRepository extends JpaRepository<CallBoard,Integer> , JpaSpecificationExecutor<CallBoard>{
  
}