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

   

}