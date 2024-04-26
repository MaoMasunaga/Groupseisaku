package jp.ac.ohara.point_manager.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.ac.ohara.point_manager.model.TestModel;
import jp.ac.ohara.point_manager.repository.TestRepository;

@Service
public class TestService {
    
    @Autowired
    private TestRepository testRepository;
    
    // テストを追加するメソッド
    public TestModel addTest(TestModel test) {
        return testRepository.save(test);
    }
    
    // 全てのテストを取得するメソッド
    public List<TestModel> getAllTests() {
        return testRepository.findAll();
    }
    
    // 特定の科目のテストを取得するメソッド
    public List<TestModel> getTestsBySubject(Long subjectId) {
        return testRepository.findBySubjectCd(subjectId);
    }
}

