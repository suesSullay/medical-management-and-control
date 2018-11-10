package com.mmc.service;

import com.mmc.model.User;
import com.mmc.repository.UserRepository;

import java.util.List;

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
}