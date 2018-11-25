package com.mmc.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.Data;

@Entity
@Data
public class Expert {
	  @Id
	  @GeneratedValue(strategy=GenerationType.IDENTITY)
	  private Integer id;
	  private String name;
	  private String birthday;
	  private String company;
	  //职务
	  private String post;
	  //职称
	  private String title;
	  //专业
	  private String major;
	  private String tel;
	  private String mail;
	  private String idNo;
	  private String context;
	  //添加时间
	  private Date createTime;
	  //性别
	  private int sex;
	  //履历
	  @OneToMany(cascade=CascadeType.ALL)
	  @JoinColumn(name="eid")
	  private List<Record> records;
	  @ManyToOne
	  @JoinColumn(name="uid")
	  //建立用户
	  private User user;
}
