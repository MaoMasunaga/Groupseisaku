package jp.ac.ohara.point_manager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jp.ac.ohara.point_manager.model.TestModel;


@Repository
public interface TestRepository extends JpaRepository<TestModel, Long> {
 
}
