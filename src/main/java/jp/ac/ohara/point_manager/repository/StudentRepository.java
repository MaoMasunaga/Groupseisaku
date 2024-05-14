package jp.ac.ohara.point_manager.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jp.ac.ohara.point_manager.model.StudentModel;

@Repository
public interface StudentRepository extends JpaRepository<StudentModel, Long> {
	//入学年度
    List<StudentModel> findByEntYear(Integer entYear);
    //クラス
    List<StudentModel> findByClassNum(String classNum);
    //在学状況
    List<StudentModel> findByIsAttend(Boolean isAttend);
    //入学年度とクラス
    List<StudentModel> findByEntYearAndClassNum(Integer entYear, String classNum);
    //入学年度と在学状況
    List<StudentModel> findByEntYearAndIsAttend(Integer entYear, Boolean isAttend);
    //クラスと在学状況
    List<StudentModel> findByClassNumAndIsAttend(String classNum, Boolean isAttend);
    //入学年度とクラスと在学状況
    List<StudentModel> findByEntYearAndClassNumAndIsAttend(Integer entYear, String classNum, Boolean isAttend);
    
    //成績登録用
    //入学年度とクラスと学生番号と氏名
    List<StudentModel>findByEntYearAndClassNumAndNoAndName(Integer entYear, String classNum, String no, String name);
    
    List<StudentModel> findBySchoolCd(String schoolCd);
    
    
    StudentModel findByNo(String studentNo);
}
