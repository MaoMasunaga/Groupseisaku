package jp.ac.ohara.point_manager.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jp.ac.ohara.point_manager.model.StudentModel;
import jp.ac.ohara.point_manager.service.StudentService;

@Controller
public class MainController {
    
    @Autowired
    private StudentService studentService;

    @GetMapping("/")
    public String index(Model model) {
        return "index";
    }


	// 学生登録ページ
    @GetMapping("/setstu/")
    public ModelAndView add(ModelAndView model) {
        model.addObject("student", new StudentModel()); 
        model.setViewName("setstu");
        return model;
    }

    @PostMapping("/setstu/")
    public String student(@Validated @ModelAttribute StudentModel student, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            // バリデーションエラーがある場合の処理
            return "setstu"; // エラーがあるため、登録ページに戻る
        }
        try {
            studentService.saveOrUpdateStudent(student);
            redirectAttributes.addFlashAttribute("exception", "");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("exception", e.getMessage());
        }
        return "redirect:/";
    }
	
	
    // 学生登録ページ
    @GetMapping("/setstu/")
    public String add(Model model) {
        model.addAttribute("student", new StudentModel()); 
        return "setstu";
    }

    //Form送信
    @PostMapping("/setstu/")
    public String student(@ModelAttribute StudentModel student, RedirectAttributes redirectAttributes) {
        try {
            studentService.saveOrUpdateStudent(student);
            redirectAttributes.addFlashAttribute("exception", "");

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("exception", e.getMessage());
        }
        return "redirect:/";

    }

    //学生・成績・出席リスト表示・出席詳細表示
    @GetMapping("/studentlist/")
    public String showStudentList(Model model) {
        model.addAttribute("studentList", studentService.getStudentList());
        return "studentlist";
    }

    // 学生情報の削除
    @PostMapping("/setstu/delete/{id}")
    public String deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return "redirect:/studentlist/";
    }

    // 学生情報の編集ページ
    @GetMapping("/studentlist/edit/{id}")
    public String editStudent(@PathVariable Long id, Model model) {
        StudentModel student = studentService.getStudentById(id);
        model.addAttribute("student", student);
        
        // 入学年度の選択肢を設定
        List<Integer> years = Arrays.asList(2014, 2015, 2016, 2017, 2018, 2019, 2020, 2021, 2022, 2023, 2024, 2025, 2026, 2027, 2028, 2029, 2030, 2031, 2032, 2033, 2034);
        model.addAttribute("years", years);
        
        // クラス番号の選択肢を設定
        List<String> classNumbers = Arrays.asList("101", "102", "103", "104", "105", "201", "202", "203", "204", "205", "301", "302", "303", "304", "305");
        model.addAttribute("classNumbers", classNumbers);
        
        return "edit_student";
    }

    // 学生情報の更新
    @PostMapping("/studentlist/update/{id}")
    public String updateStudent(@PathVariable Long id, @ModelAttribute StudentModel student, RedirectAttributes redirectAttributes) {
        student.setId(id);
        try {
            studentService.saveOrUpdateStudent(student);
            redirectAttributes.addFlashAttribute("exception", "");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("exception", e.getMessage());
        }
        return "redirect:/studentlist/";
    }
    
    

}
