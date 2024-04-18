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
import jp.ac.ohara.point_manager.model.SubjectModel;
import jp.ac.ohara.point_manager.service.SubjectService;




@Controller
public class SubjectController {
	
	@Autowired
	private SubjectService subjectService;
	

	// 成績登録ページ
	@GetMapping("/subject/")
	public ModelAndView add(SubjectModel subject, ModelAndView model) {
		  model.addObject("subject", subject); 
		  model.setViewName("subject");
		  return model;
	}
	
	//Form送信
	@PostMapping("/subject/")
	public String subject(@Validated @ModelAttribute @NonNull SubjectModel subject, RedirectAttributes result, ModelAndView model,
	        RedirectAttributes redirectAttributes) {
	    try {
	        this.subjectService.save(subject);
	        redirectAttributes.addFlashAttribute("exception", "");

	    } catch (Exception e) {
	        redirectAttributes.addFlashAttribute("exception", e.getMessage());
	    }
	    return "redirect:/";

	  }
	

	//学生・成績・出席リスト表示・出席詳細表示
	@GetMapping("/subjectlist/")
	public String add3(Model model) {
	  System.out.println(subjectService.getSubjectList().toString());
	    model.addAttribute("subjectList", subjectService.getSubjectList());
	    return "subjectlist";
	}

	// 成績情報の削除
	@PostMapping("/subject/delete/{id}")
	public String deleteSubject(@PathVariable Long id) {
	    subjectService.delete(id);
	    	return "redirect:/subjectlist/";
	}
		
		// 成績情報の編集ページ
	@GetMapping("/subjectlist/edit/{id}")
	public String editSubject(@PathVariable Long id, Model model) {
	    SubjectModel subject = subjectService.getSubjectById(id);
	    model.addAttribute("subject", subject);
	    return "edit_subject";

	}
}
