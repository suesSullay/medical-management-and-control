package com.mmc.repository;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mmc.model.Rule;
import com.mmc.model.User;

public interface UserRepository extends JpaRepository<User,Integer> {

	User findByUserNameAndPassWord(String userName, String passWord);
	
	List<User> findByRule(Rule rule);
	
}