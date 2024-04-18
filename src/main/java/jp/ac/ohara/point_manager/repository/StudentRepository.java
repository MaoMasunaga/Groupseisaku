package jp.ac.ohara.point_manager.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jp.ac.ohara.point_manager.model.StudentModel;

@Repository
public interface StudentRepository extends JpaRepository<StudentModel, Long> {
    List<StudentModel> findByEntYearAndClassNumAndIsAttend(Integer enrollmentYear, String className, Boolean isActive);
}
