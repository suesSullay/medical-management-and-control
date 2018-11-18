package com.mmc.model;

import java.util.Date;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import lombok.Data;

/**
 * Task
 */
@Entity
@Data
public class Task {
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  private Integer id;
  private String name;
  private Date createTime;
  private Date updateTime;
  @ManyToOne
  @JoinColumn(name="tid")
  private TaskType taskType;
  
  private String context;
/**
 * json 格式
 * "[C:/3123.txt]"  
 */
  private String files;
  /**
   * 
   * user为用户ID
   * Json 格式
   * {
   * 	"user1":{
   * 		"state":true,
   * 		"files":"['C:/file/123.txt']"
   * 	},
   * 	"user2":{
   * 		"state":true,
   * 		"files":"['C:/file/123.txt']"
   * 	},
   *		
   * }
   * 
   * 
   */  
  @Lob
  private String users;
  private Integer userNum;
  private Integer EndNum;
}
