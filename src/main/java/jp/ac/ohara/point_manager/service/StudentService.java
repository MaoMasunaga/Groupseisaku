package jp.ac.ohara.point_manager.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

	 // 学生データの保存
	    public void save(StudentModel student) {
	        repository.save(student);
	    }
    
    // 学生データの保存
    public void saveOrUpdateStudent(StudentModel student) {
        repository.save(student);
    }

    // 学生データの削除
    public void deleteStudent(Long id) {
        repository.deleteById(id);
    }

    // 入学年度、クラス番号、在学中か否かで絞り込み検索
    public List<StudentModel> filterStudents(Integer enrollmentYear, String className, Boolean isActive) {
        // 入学年度、クラス番号、在籍状況のいずれかが指定された場合は絞り込み検索を実行
        if (enrollmentYear != null || className != null || isActive != null) {
            return repository.findByEntYearAndClassNumAndIsAttend(enrollmentYear, className, isActive);
        } else {
            // すべての条件がnullの場合は全ての学生情報を返す
            return repository.findAll();
        }
    }

    // IDを指定して学生データを取得
    public StudentModel getStudentById(Long id) {
        return repository.findById(id).orElse(null);
    }

}
