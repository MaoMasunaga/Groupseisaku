package jp.ac.ohara.point_manager.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import jp.ac.ohara.point_manager.model.StudentModel;
import jp.ac.ohara.point_manager.model.TestModel;
import jp.ac.ohara.point_manager.repository.StudentRepository;
import jp.ac.ohara.point_manager.repository.TestRepository;

@Service
public class TestService {
	
    @Autowired
    private StudentRepository repository;
    @Autowired
    private TestRepository testRepository;
    
    // 学生一覧の取得
    public List<StudentModel> getStudentList() {
        return repository.findAll();
    }

    // 詳細データの取得
    public StudentModel get(@NonNull Long index) {
        return repository.findById(index).orElse(null);
    }
    
    // 全ての学生データを取得
    public List<StudentModel> getStudentList1() {
        return repository.findAll();
    }
    
    public List<StudentModel> searchStudents(Integer entYear, String classNum, String no, String name) {
            // 入学年度、クラス番号、学籍番号、名前がすべて指定された場合
            return repository.findByEntYearAndClassNumAndNoAndName(entYear, classNum, no ,name );
            
    }
    
    public List<TestModel> getAllStudentsBySchoolCd(String schoolCd) {
        // schoolCdに基づいて生徒のデータをデータベースから取得するロジックを実装する
        return testRepository.findAll();
    }
   
    	
    }
            
  
    
    
