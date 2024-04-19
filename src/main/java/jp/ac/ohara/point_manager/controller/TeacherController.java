package jp.ac.ohara.point_manager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import io.micrometer.common.lang.NonNull;
import jp.ac.ohara.point_manager.model.TeacherModel;
import jp.ac.ohara.point_manager.service.TeacherService;




@Controller
public class TeacherController {
	
	@Autowired
	private TeacherService teacherService;
	

	// 先生登録ページ
	@GetMapping("/teacher/")
	public ModelAndView add(TeacherModel teacher, ModelAndView model) {
		  model.addObject("teacher", teacher); 
		  model.setViewName("teacher");
		  return model;
	}
	
	//Form送信
	@PostMapping("/teacher/")
	public String teacher(@Validated @ModelAttribute @NonNull TeacherModel teacher, RedirectAttributes result, ModelAndView model,
	        RedirectAttributes redirectAttributes) {
	    try {
	        this.teacherService.save(teacher);
	        redirectAttributes.addFlashAttribute("exception", "");

	    } catch (Exception e) {
	        redirectAttributes.addFlashAttribute("exception", e.getMessage());
	    }
	    return "redirect:/";

	  }
	

	//先生・先生・出席リスト表示・出席詳細表示
	@GetMapping("/teacherlist/")
	public String add3(Model model) {
	  System.out.println(teacherService.getTeacherList().toString());
	    model.addAttribute("teacherList", teacherService.getTeacherList());
	    return "teacherlist";
	}

	// 先生情報の削除
	@PostMapping("/teacher/delete/{id}")
	public String deleteTeacher(@PathVariable Long id) {
	    teacherService.delete(id);
	    	return "redirect:/teacherlist/";
	}
		
		// 先生情報の編集ページ
	@GetMapping("/teacherlist/edit/{id}")
	public String editTeacher(@PathVariable Long id, Model model) {
	    TeacherModel teacher = teacherService.getTeacherById(id);
	    model.addAttribute("teacher", teacher);
	    return "edit_teacher";

	}
}
