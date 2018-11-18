package com.mmc.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

/**
 * user
 */
@Entity
@Data
public class User {
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  private Integer id;
  @Column(unique=true,nullable=false)
  private String name;
  @Column(unique=true,nullable=false)
  private String userName;
  @Column(nullable=false)
  private String passWord;
  @Enumerated(EnumType.ORDINAL)
  @Column(nullable=false)
  private Rule rule;
}
