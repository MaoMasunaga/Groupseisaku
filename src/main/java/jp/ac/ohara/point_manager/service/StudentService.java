package jp.ac.ohara.point_manager.service;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import jp.ac.ohara.point_manager.model.StudentModel;
import jp.ac.ohara.point_manager.repository.StudentRepository;
 

@Service
@Transactional
public class  StudentService{

    @Autowired
    private StudentRepository repository;

    /**
     * 学生一覧の取得
     * @return List<StudentModel>
     */
    public List<StudentModel> getStudentList() {
        List<StudentModel> entityList = this.repository.findAll();
        return entityList;
    }

    /**
     * 詳細データの取得
     * @param @NonNull Long index

     */
    public StudentModel get(@NonNull Long index) {
        StudentModel student = this.repository.findById(index).orElse(new StudentModel());
        return student;
    }

    public void save(@NonNull StudentModel student) {
    	System.out.println(student);
        this.repository.save(student);
    }



//学生データの削除・編集

	public void delete(@NonNull Long index) {
		this.repository.deleteById(index);
	}
	

    public List<StudentModel> getStudentList1() {
        return repository.findAll();
    }

    public StudentModel getStudentById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public void saveOrUpdateStudent(StudentModel student) {
        repository.save(student);
    }

    public void deleteStudent(Long id) {
        repository.deleteById(id);
    }
}

		
	
