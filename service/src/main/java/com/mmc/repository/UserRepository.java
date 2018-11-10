package com.mmc.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.mmc.model.User;

public interface UserRepository extends JpaRepository<User,Integer> {
  
}