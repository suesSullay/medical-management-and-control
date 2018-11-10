package com.mmc.controller;

import com.mmc.model.User;
import com.mmc.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * userController
 */
@RestController
@RequestMapping("/user")
public class userController {
  @Autowired
  UserService userService;
  @PostMapping("/")
  public void save(){
    var user=new User();
    user.setName("杨金伟");
    userService.create(user);
  }
}