package jp.ac.ohara.point_manager.model;

import java.util.List;

public class StudentPointsWrapper {
    private List<StudentPoint> students;

    // getterとsetter
    public List<StudentPoint> getStudents() {
        return students;
    }

    public void setStudents(List<StudentPoint> students) {
        this.students = students;
    }
}