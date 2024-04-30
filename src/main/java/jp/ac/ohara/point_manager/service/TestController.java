package jp.ac.ohara.point_manager.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jp.ac.ohara.point_manager.model.TestModel;
import jp.ac.ohara.point_manager.service.TestService;

@RestController
@RequestMapping("/subjects")
public class TestController {
    
    @Autowired
    private TestService testService;
    
    // POSTリクエストを受け取り、新しいテストを追加するエンドポイント
    @PostMapping("/{subjectId}/tests")
    public ResponseEntity<TestModel> addTest(@PathVariable Long subjectId, @RequestBody TestModel testmodel) {
        // TestServiceを使用してテストを追加
        TestModel createdTest = testService.addTest(testmodel);
        return ResponseEntity.ok(createdTest);
    }
    
    // GETリクエストを受け取り、特定の科目のテストを取得するエンドポイント
    @GetMapping("/{subjectId}/tests")
    public ResponseEntity<List<TestModel>> getTestsBySubject(@PathVariable Long subjectId) {
        // TestServiceを使用して特定の科目のテストを取得
        List<TestModel> testsmodel = testService.getTestsBySubject(subjectId);
        return ResponseEntity.ok(testsmodel);
    }
}

