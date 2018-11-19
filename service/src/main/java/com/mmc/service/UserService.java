package com.mmc.service;

import com.mmc.model.Rule;
import com.mmc.model.User;
import com.mmc.repository.UserRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
  @Autowired
  UserRepository userRepository;
  public void create(User user) {
    userRepository.save(user);
  }
  public void delete(User user) {
    userRepository.delete(user);
  }
  public void update(User user) {
    userRepository.save(user);
  }
  public List<User> findAll() {
    return userRepository.findAll();
  }
  public User findOne(Integer id) {
	  return userRepository.findById(id).get();
  }
  public User findByUserNameAndPassWord(String userName,String passWord) {
	  return userRepository.findByUserNameAndPassWord(userName,passWord);
  }
  public List<User> findByRule(Rule rule){
	  return userRepository.findByRule(rule);
  }
}