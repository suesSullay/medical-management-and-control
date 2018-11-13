package com.mmc.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import lombok.Data;

@Entity
@Data
public class Expert {
	  @Id
	  @GeneratedValue(strategy=GenerationType.IDENTITY)
	  private Integer id;
	  private String name;
	  //添加时间
	  private Date createTime;
	  //性别
	  private Boolean sex;
	  //履历
	  @OneToMany(mappedBy="records",cascade=CascadeType.ALL)
	  @JoinColumn(name="eid")
	  private List<Record> records;
}
