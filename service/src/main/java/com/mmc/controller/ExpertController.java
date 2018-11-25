package com.mmc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mmc.model.Msg;
import com.mmc.model.Expert;
import com.mmc.model.User;
import com.mmc.service.ExpertService;
import com.mmc.service.UserService;

/**
 * userController
 */
@CrossOrigin
@RestController
@RequestMapping("/expert")
public class ExpertController {
  @Autowired
  ExpertService expertService;
  @Autowired
  UserService userService;
  @PostMapping("/")
  public Msg create(@RequestBody Expert expert) {
	  expertService.create(expert);
	  return Msg.success();
  }
  @DeleteMapping("/")
  public Msg delete(@RequestBody Expert expert) {
	  expertService.delete(expert);
	  return Msg.success();
  }
  @GetMapping("/all")
  public Msg findPage(@RequestParam("userName") String userName ,@RequestParam(value="keyName",defaultValue="") String keyName ,@RequestParam(value="page",defaultValue="0") int page,@RequestParam(value="size",defaultValue="7") int size){
	  User user = userService.findByName(userName);
	  Page<Expert> expertPage = expertService.findPage(user, keyName,page, size);
	  return Msg.success().add("expertPage", expertPage);
  }
}