package com.mmc.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.mmc.model.Message;
import com.mmc.model.Task;
import com.mmc.model.User;

public interface MessageRepository extends JpaRepository<Message,Integer> , JpaSpecificationExecutor<Message>{
  
}