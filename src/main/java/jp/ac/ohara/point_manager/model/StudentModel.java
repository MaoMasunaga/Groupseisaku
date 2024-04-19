package jp.ac.ohara.point_manager.model;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="student")

public  class StudentModel{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private long id;
	
	@Column(length = 10, nullable = false, name = "no")
	private String no;
	
	@Column(length = 10, nullable = true, name = "name")
	private String name;
	
	@Column(length = 10, nullable = true, name = "ent_year")
	private Integer entYear;
	
	@Column(length = 3, nullable = true, name = "class_num")
	private String classNum;
	
    @Column(nullable = true, name = "is_attend")
    private Boolean isAttend = true; // デフォルトで在籍中とする
	
	@Column(length = 3, nullable = true, name = "school_cd")
	private String schoolCd;
	
	@Override
	public boolean equals(Object obj) {
	    if (this == obj) {
	        return true;
	    }
	    if (obj == null || getClass() != obj.getClass()) {
	        return false;
	    }
	    StudentModel other = (StudentModel) obj;
	    return Objects.equals(id, other.id) &&
	           Objects.equals(no, other.no) &&
	           Objects.equals(name, other.name) &&
	           Objects.equals(entYear, other.entYear) &&
	           Objects.equals(classNum, other.classNum) &&
	           Objects.equals(isAttend, other.isAttend) &&
	           Objects.equals(schoolCd, other.schoolCd);
	}

	@Override
	public int hashCode() {
	    return Objects.hash(id, no, name, entYear, classNum, isAttend, schoolCd);
	}

	


	}


	
	
	
	
	
	


