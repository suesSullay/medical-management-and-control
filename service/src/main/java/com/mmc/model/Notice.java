package com.mmc.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;

/**
 * 公告管理
 * 
 * 分中心创建上传资源
 *
 */
@Entity
@Data
public class Notice {
	  @Id
	  @GeneratedValue(strategy=GenerationType.IDENTITY)
	  private Integer id;
	  @ManyToOne
	  @JoinColumn(name="uid")
	  //建立用户
	  private User user;
	  
	  private Date createTime;
	  
	  private String[] files; 
}
