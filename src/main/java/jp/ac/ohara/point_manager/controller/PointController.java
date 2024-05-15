package jp.ac.ohara.point_manager.controller;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jp.ac.ohara.point_manager.model.StudentModel;
import jp.ac.ohara.point_manager.model.SubjectModel;
import jp.ac.ohara.point_manager.model.TeacherModel;
import jp.ac.ohara.point_manager.model.TestModel;
import jp.ac.ohara.point_manager.repository.StudentRepository;
import jp.ac.ohara.point_manager.repository.TestRepository;
import jp.ac.ohara.point_manager.service.StudentService;
import jp.ac.ohara.point_manager.service.SubjectService;
import jp.ac.ohara.point_manager.service.TestService;

@Controller
public class PointController {

    @Autowired
    private TestRepository testRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private TestService testService;
    @Autowired
    private SubjectService subjectService;
    @Autowired
    private StudentService studentService;
 // 成績ページ
    
	@GetMapping("/point/")
	public String getAllStudents(Model model,@AuthenticationPrincipal TeacherModel teachermodel ,@AuthenticationPrincipal TeacherModel teacher, @AuthenticationPrincipal StudentModel student, @AuthenticationPrincipal SubjectModel subject) {               
		TestModel testmodel = new TestModel();
		String schoolCd = teacher.getSchoolCd();               
		List<TestModel> students = testService.getAllStudentsBySchoolCd(schoolCd);
		model.addAttribute("tests", students);
		model.addAttribute("testmodel",testmodel);
		List<StudentModel> studentList = studentService.getStudentEntYear(schoolCd);
		model.addAttribute("student", studentList);
		List<SubjectModel> subjectCd = subjectService.getAllSubjectBySchoolCd(schoolCd);
		model.addAttribute("subjectCd", subjectCd);  
		model.addAttribute("user2",teachermodel);
		return "point"; 
	}

    @ModelAttribute("subjects")
    public List<String> populateSubjects() {
        // subjecttableから科目名を取得してリストとして返す
        return subjectService.getAllSubjects();
    }
    
  
    @ModelAttribute("years")
    public List<Integer> getYears() {
        List<Integer> years = new ArrayList<>();
        IntStream.rangeClosed(2014, 2034).forEach(years::add);
        return years;
    }
    
    @ModelAttribute("no")
    public List<Integer> getNo() {
        List<Integer> no = new ArrayList<>();
        IntStream.rangeClosed(1,2).forEach(no::add);
        return no;
    }

    @ModelAttribute("classNumbers")
    public List<String> getClassNumbers() {
        List<String> classNumbers = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            for (int j = 1; j <= 5; j++) {
                classNumbers.add(i + "0" + j);
            }
        }
        return classNumbers;
    }
    
    @GetMapping("/point/search") 
    public String searchStudents(
            @RequestParam(value = "entYear", required = false) Integer entYear,
            @RequestParam(value = "classNum", required = false) String classNum,
            Model model) {
	    	
			
	        // 条件がnullでない場合のみ検索に使用する
	        List<StudentModel> students;
        if (entYear != null || classNum != null) {
            students = studentService.searchStudents2(entYear, classNum);
        } else {
            // 全ての条件がnullの場合は全ての学生を表示
            students = studentService.getStudentList();
        }
        model.addAttribute("studentList", students);
        return "point";
    }
    
    @PostMapping("/point/")
    public String registerPoint(@RequestParam("studentNo") String studentNo,
                                @RequestParam("subjectCd") String subjectCd,
                                @RequestParam("schoolCd") int schoolCd,
                                @RequestParam("no") int no,
                                @RequestParam("point") int point,
                                @RequestParam("classNum") String classNum) {

        // 学生番号を元に学生情報を取得
        StudentModel student = studentRepository.findByNo(studentNo);

        // 学生情報が存在する場合、点数を登録
        if (student != null) {
            TestModel testModel = new TestModel();
            testModel.setStudent(student);
            testModel.setSubjectCd(subjectCd);
            testModel.setSchoolCd(schoolCd);
            testModel.setNo(no);
            testModel.setPoint(point);
            testModel.setClassNum(classNum);

            testRepository.save(testModel);
            return "redirect:/point/?success";
        } else {
            return "redirect:/point/?error=student_not_found";
        }
    }
        

    }


