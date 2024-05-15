package jp.ac.ohara.point_manager.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="test")
public class TestModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private long id;

    @Column(length = 3, nullable = false, name = "subject_cd")
    private String subjectCd;

    @Column(length = 3, nullable = false, name = "school_cd")
    private String schoolCd;

    @Column(length = 10, nullable = false, name = "no")
    private Integer no;

    @Column(length = 10, nullable = true, name = "point")
    private Integer point;

    @Column(length = 5, nullable = true, name = "class_num")
    private String classNum;

    @ManyToOne
    @JoinColumn(name = "student_no", referencedColumnName = "no")
    private StudentModel student;

}
