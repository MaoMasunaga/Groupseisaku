package jp.ac.ohara.point_manager.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import jp.ac.ohara.point_manager.model.TeacherModel;
import jp.ac.ohara.point_manager.repository.TeacherRepository;
 
@Service
@Transactional
public class  TeacherService{

    @Autowired
    private TeacherRepository repository;
    
	@Autowired    
	private PasswordEncoder passwordEncoder;

    //科目一覧の取得
     
    public List<TeacherModel> getTeacherList() {
        List<TeacherModel> entityList = this.repository.findAll();
        return entityList;
    }

    //詳細データの取得
    
    public TeacherModel get(@NonNull Long index) {
    	TeacherModel teacher = this.repository.findById(index).orElse(new TeacherModel());
        return teacher;
    }

		//科目データの削除・編集
	
		public void delete(@NonNull Long index) {
			this.repository.deleteById(index);
		}
		
	    public List<TeacherModel> getTeacherList1() {
	        return repository.findAll();
	    }
	
	    public TeacherModel getTeacherById(Long id) {
	        return repository.findById(id).orElse(null);
	    }
	
	    public void saveOrUpdateTeacher(TeacherModel teacher) {
	        repository.save(teacher);
	    }
	
	    public void deleteTeacher(Long id) {
	        repository.deleteById(id);
	    }
	    
	    public void save(@NonNull TeacherModel teacher) {
	    	teacher.setPassword(this.passwordEncoder.encode(teacher.getPassword()));
	        this.repository.save(teacher);
	    }

	}

		
	
