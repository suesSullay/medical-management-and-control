package com.mmc.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;
/**
 * 
 * 专家履历
 * 
 */
@Entity
@Data
public class Record {
	  @Id
	  @GeneratedValue(strategy=GenerationType.IDENTITY)
	  private Integer id;
	  //添加时间
	  private Date createTime;
	  private Date endTime;
	  private String type;
	  private String hospital;
	  private String context;
}
