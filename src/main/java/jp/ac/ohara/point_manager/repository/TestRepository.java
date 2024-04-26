package jp.ac.ohara.point_manager.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jp.ac.ohara.point_manager.model.TestModel;


@Repository
public interface TestRepository extends JpaRepository<TestModel, Long> {
    // subjectCdに基づいてテストを取得するためのメソッドを定義
    List<TestModel> findBySubjectCd(Long subjectId);
}
