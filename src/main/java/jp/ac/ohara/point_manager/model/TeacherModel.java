package jp.ac.ohara.point_manager.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="teacher")
 
public  class TeacherModel{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private long id;
	@Column(length = 10, nullable = false, name = "tid")
	private String tid;
	@Column(length = 30, nullable = true, name = "password")
	private String password;
	@Column(length = 10, nullable = true, name = "name")
	private String name;
	@Column(length = 3, nullable = true, name = "school_cd")
	private String schoolCd;
 
 
	}