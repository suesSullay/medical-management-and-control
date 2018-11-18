package com.mmc.controller;

import com.mmc.model.Msg;
import com.mmc.model.User;
import com.mmc.service.UserService;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * userController
 */
@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {
  @Autowired
  UserService userService;
  @PostMapping("/login")
  public Msg Login(@RequestBody User user){
	User res = userService.findByUserNameAndPassWord(user.getUserName(), user.getPassWord());
	if(res==null) {
		return Msg.fail();
	}
	return Msg.success().add("user", res);
  }
}