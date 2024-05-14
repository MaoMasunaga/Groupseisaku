package jp.ac.ohara.point_manager.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import jp.ac.ohara.point_manager.model.SubjectModel;
import jp.ac.ohara.point_manager.repository.SubjectRepository;
 
@Service
@Transactional
public class  SubjectService{

    @Autowired
    private SubjectRepository repository;

    //科目一覧の取得
     
    public List<SubjectModel> getSubjectList() {
        List<SubjectModel> entityList = this.repository.findAll();
        return entityList;
    }

    //詳細データの取得
    
    public SubjectModel get(@NonNull Long index) {
    	SubjectModel subject = this.repository.findById(index).orElse(new SubjectModel());
        return subject;
    }

    public void save(@NonNull SubjectModel subject) {
    	System.out.println(subject);
        this.repository.save(subject);
    }



		//科目データの削除・編集
	
		public void delete(@NonNull Long index) {
			this.repository.deleteById(index);
		}
		
	    public List<SubjectModel> getSubjectList1() {
	        return repository.findAll();
	    }
	
	    public SubjectModel getSubjectById(Long id) {
	        return repository.findById(id).orElse(null);
	    }
	
	    public void saveOrUpdateSubject(SubjectModel subject) {
	        repository.save(subject);
	    }
	
	    public void deleteSubject(Long id) {
	        repository.deleteById(id);
	    }

	    public List<String> getAllSubjects() {
	        // subjectRepositoryを使用してsubjecttableからすべての科目名を取得
	        List<SubjectModel> subjects = repository.findAll();

	        // 科目名を格納するリストを作成
	        List<String> subjectNames = new ArrayList<>();

	        // Subjectエンティティから科目名を取り出してリストに追加
	        for (SubjectModel subject : subjects) {
	            subjectNames.add(subject.getName());
	        }

	        return subjectNames;
	    }
	    
	    public List<SubjectModel> getAllSubjectBySchoolCd(String schoolCd) {
	        // schoolCdに基づいて科目のデータをデータベースから取得するロジックを実装する
	        return repository.findBySchoolCd(schoolCd);
	    }
	}

		
	
