package com.mmc.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
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
public class CallBoard {
	  @Id
	  @GeneratedValue(strategy=GenerationType.IDENTITY)
	  private Integer id;
	  private String name;
	  @Lob
	  private String context;
	  
	  private Date createTime;
	  @Lob
	  //json格式 ['c://2323.txt','d://32131.zip']
	  private String files; 
}
