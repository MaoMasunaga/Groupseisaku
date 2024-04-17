package jp.ac.ohara.point_manager.model;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Table(name="classnum")
 
public  class Class_numModel{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private long id;
	@Column(length = 3, nullable = false, name = "school_cd")
	private String schoolCd;
	@Column(length = 5, nullable = false, name = "class_num")
	private String classNum;
 
 
 
 
	}