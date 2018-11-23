package com.mmc.controller;

import com.mmc.model.Msg;
import com.mmc.model.Rule;
import com.mmc.model.User;
import com.mmc.service.UserService;

import java.util.List;
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
  @GetMapping("/common")
  public Msg findCommonUser() {
	  List<User> list = userService.findByRule(Rule.COMMON);
	  return Msg.success().add("commonUsers", list);
  }
  @GetMapping("/all")
  public Msg findAll() {
	  List<User> list = userService.findAll();
	  return Msg.success().add("userList", list);
  }
  @GetMapping("/")
  public Msg findByName(@RequestParam("name") String name) {
	  User user = userService.findByName(name);
	  return Msg.success().add("user", user);
  }
}