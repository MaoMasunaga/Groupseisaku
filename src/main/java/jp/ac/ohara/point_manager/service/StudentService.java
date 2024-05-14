package jp.ac.ohara.point_manager.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.ac.ohara.point_manager.model.StudentModel;
import jp.ac.ohara.point_manager.repository.StudentRepository;

@Service
@Transactional
public class StudentService {

    @Autowired
    private StudentRepository repository;

    // 学生一覧の取得
    public List<StudentModel> getStudentList() {
        return repository.findAll();
    }
    
    // 学生一覧の取得(点数登録)
    public List<StudentModel> getStudentList2(Integer entYear, String classNum) {
        return repository.findByEntYearAndClassNum(entYear, classNum);
    }

    // 詳細データの取得
    public StudentModel get(@NonNull Long index) {
        return repository.findById(index).orElse(null);
    }

    // 学生データの保存
    public void save(@NonNull StudentModel student) {
        repository.save(student);
    }

    // 学生データの削除
    public void delete(@NonNull Long index) {
        repository.deleteById(index);
    }

    // 全ての学生データを取得
    public List<StudentModel> getStudentList1() {
        return repository.findAll();
    }

    // IDによる学生データの取得
    public StudentModel getStudentById(Long id) {
        return repository.findById(id).orElse(null);
    }

    // 学生データの保存または更新
    public void saveOrUpdateStudent(StudentModel student) {
        repository.save(student);
    }

    // 学生データの削除
    public void deleteStudent(Long id) {
        repository.deleteById(id);
    }
    
    public List<StudentModel> searchStudents(Integer entYear, String classNum, Boolean isAttend) {
        if (entYear != null && classNum != null && isAttend != null) {
            // 入学年度、クラス番号、在学中か否かがすべて指定された場合
            return repository.findByEntYearAndClassNumAndIsAttend(entYear, classNum, isAttend);
        } else if (entYear != null && classNum != null) {
            // 入学年度とクラス番号が指定された場合
            return repository.findByEntYearAndClassNum(entYear, classNum);
        } else if (entYear != null && isAttend != null) {
            // 入学年度と在籍の有無が指定された場合
            return repository.findByEntYearAndIsAttend(entYear, isAttend);
        } else if (classNum != null && isAttend != null) {
            // クラス番号と在籍の有無が指定された場合
            return repository.findByClassNumAndIsAttend(classNum, isAttend);
        } else if (entYear != null) {
            // 入学年度のみが指定された場合
            return repository.findByEntYear(entYear);
        } else if (classNum != null) {
            // クラス番号のみが指定された場合
            return repository.findByClassNum(classNum);
        } else if (isAttend != null) {
            // 在籍の有無のみが指定された場合
            return repository.findByIsAttend(isAttend);
        } else {
            // 全ての条件が指定されなかった場合、空のリストを返す
            return repository.findAll();
        }
    }

	public List<StudentModel> searchStudents2(Integer entYear, String classNum) {
		// TODO 自動生成されたメソッド・スタブ
		return repository.findByEntYearAndClassNum(entYear, classNum);
		
	}
	
	public List<StudentModel> getStudentEntYear(String schoolCd) {
        // schoolCdに基づいて学生の入学年度をデータベースから取得するロジックを実装する
        return repository.findBySchoolCd(schoolCd);
    }

	


   

}