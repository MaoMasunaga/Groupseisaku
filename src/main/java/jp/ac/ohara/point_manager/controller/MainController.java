package jp.ac.ohara.point_manager.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import io.micrometer.common.lang.NonNull;
import jp.ac.ohara.point_manager.model.StudentModel;
import jp.ac.ohara.point_manager.model.TeacherModel;
import jp.ac.ohara.point_manager.service.StudentService;

@Controller
public class MainController {
	
    @Autowired
    private StudentService studentService;
    
    
    //ログインページ
    @GetMapping("/login/")
    public ModelAndView getLogin(TeacherModel teachermodel, ModelAndView model) {
  	  model.addObject("teachermodel", teachermodel);
  	  model.setViewName("login");
  	  return model;
    }
 
    @PostMapping("/login/")
	public String postLogin(@PathVariable("id") Long id , Model model) {
    	System.out.println("aaa");
		return "redirect:index";

	}
    
    @GetMapping("/logout/")
    public String logout() {
        // ログアウト処理を行う
        // ここでセッションを無効化したり、必要なクリア処理を行います
        return "logout"; // logout.html を表示させる
    }

    @GetMapping("/")
    public String index(@AuthenticationPrincipal TeacherModel teachermodel,Model model) {
    	model.addAttribute("user2",teachermodel);

    	return "index";
    }

    // 学生登録ページ
    @GetMapping("/setstu/")
    public ModelAndView add(@AuthenticationPrincipal TeacherModel teachermodel, Model model1,StudentModel student, ModelAndView model) {
        model.addObject("student", student); 
        model.setViewName("setstu");
        model.addObject("user2",teachermodel);
       

        return model;
    }
    
    @GetMapping("/studentcomplete/")
    public ModelAndView studentcomplete(@AuthenticationPrincipal TeacherModel teachermodel, Model model1,StudentModel student, ModelAndView model) {
        model.addObject("student", student); 
        model.setViewName("studentcomplete");
        model.addObject("user2",teachermodel);
       

        return model;
    }

    //Form送信
    @PostMapping("/setstu/")
    public String student(@Validated @ModelAttribute @NonNull StudentModel student, RedirectAttributes result, ModelAndView model,
            RedirectAttributes redirectAttributes,@AuthenticationPrincipal  TeacherModel user) {
        try {
        	student.setSchoolCd(user.getSchoolCd());
            this.studentService.save(student);
            redirectAttributes.addFlashAttribute("exception", "");

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("exception", e.getMessage());
        }
        return "redirect:/studentcomplete/";

    }

    //学生・成績・出席リスト表示・出席詳細表示
    @GetMapping("/studentlist/")
    public String add3(@AuthenticationPrincipal TeacherModel teachermodel,Model model) {
        model.addAttribute("studentList", studentService.getStudentList());
        model.addAttribute("user2",teachermodel);

        return "studentlist";
    }

    // 学生情報の削除
    @PostMapping("/setstu/delete/{id}")
    public String deleteStudent(@PathVariable Long id) {
        studentService.delete(id);
        return "redirect:/studentlist/";
    }

    // 学生情報の編集ページ
    @GetMapping("/studentlist/edit/{id}")
    public String editStudent(@AuthenticationPrincipal TeacherModel teachermodel,@PathVariable Long id, Model model) {
        StudentModel student = studentService.getStudentById(id);
        model.addAttribute("student", student);
        
        // 入学年度の選択肢を設定
        List<Integer> years = Arrays.asList(2014, 2015, 2016, 2017, 2018, 2019, 2020, 2021, 2022, 2023, 2024, 2025, 2026, 2027, 2028, 2029, 2030, 2031, 2032, 2033, 2034);
        model.addAttribute("years", years);
        
        // クラス番号の選択肢を設定
        List<String> classNumbers = Arrays.asList("101", "102", "103", "104", "105", "201", "202", "203", "204", "205", "301", "302", "303", "304", "305");
        model.addAttribute("classNumbers", classNumbers);
        
        model.addAttribute("user2",teachermodel);

        
        return "edit_student";
    }

    // 学生情報の更新
    @PostMapping("/studentlist/update/{id}")
    public String updateStudent(@PathVariable Long id, @Validated @ModelAttribute @NonNull StudentModel student, RedirectAttributes redirectAttributes) {
        student.setId(id);
        try {
            studentService.saveOrUpdateStudent(student);
            redirectAttributes.addFlashAttribute("exception", "");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("exception", e.getMessage());
        }
        


        return "redirect:/studentfini/";
    }
    
    @GetMapping("/studentlist/search")
    public String searchStudents(
            @RequestParam(value = "entYear", required = false) Integer entYear,
            @RequestParam(value = "classNum", required = false) String classNum,
            @RequestParam(value = "isAttend", required = false) Boolean isAttend,
            Model model,@AuthenticationPrincipal TeacherModel teachermodel) {
    	model.addAttribute("user2",teachermodel);
        // 条件がnullでない場合のみ検索に使用する
        List<StudentModel> students;
        if (entYear != null || classNum != null || isAttend != null) {
            students = studentService.searchStudents(entYear, classNum, isAttend);
        } else {
            // 全ての条件がnullの場合は全ての学生を表示
            students = studentService.getStudentList();
        }
        model.addAttribute("studentList", students);
        return "studentlist";
    }
    
    @ModelAttribute("years")
    public List<Integer> getYears() {
        List<Integer> years = new ArrayList<>();
        IntStream.rangeClosed(2014, 2034).forEach(years::add);
        return years;
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

	@GetMapping("/studentfini/")
	public String add(@AuthenticationPrincipal TeacherModel teachermodel,Model model) {
	    model.addAttribute("user2",teachermodel);
	    return "studentfini";
	}
   
}