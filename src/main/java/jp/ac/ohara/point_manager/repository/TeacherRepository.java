package jp.ac.ohara.point_manager.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jp.ac.ohara.point_manager.model.TeacherModel;


@Repository
public interface TeacherRepository  extends JpaRepository<TeacherModel, Long>{
	TeacherModel findByTidEquals(String tid);
	
}