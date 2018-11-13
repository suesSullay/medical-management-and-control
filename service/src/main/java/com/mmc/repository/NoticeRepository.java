package com.mmc.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.mmc.model.Message;
import com.mmc.model.Notice;
import com.mmc.model.User;

public interface NoticeRepository extends JpaRepository<Notice,Integer> {
  
}