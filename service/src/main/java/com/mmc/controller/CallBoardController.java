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
import com.mmc.model.CallBoard;
import com.mmc.model.User;
import com.mmc.service.CallBoardService;
import com.mmc.service.UserService;

/**
 * userController
 */
@CrossOrigin
@RestController
@RequestMapping("/callBoard")
public class CallBoardController {
  @Autowired
  CallBoardService callBoardService;
  @Autowired
  UserService userService;
  @PostMapping("/")
  public Msg create(@RequestBody CallBoard callBoard) {
	  callBoardService.create(callBoard);
	  return Msg.success();
  }
  @DeleteMapping("/")
  public Msg delete(@RequestBody CallBoard callBoard) {
	  callBoardService.delete(callBoard);
	  return Msg.success();
  }
  @GetMapping("/all")
  public Msg findPage(@RequestParam(value="keyName",defaultValue="") String keyName ,@RequestParam(value="page",defaultValue="0") int page,@RequestParam(value="size",defaultValue="7") int size){
	  Page<CallBoard> callBoardPage = callBoardService.findPage(keyName,page, size);
	  return Msg.success().add("callBoardPage", callBoardPage);
  }
}