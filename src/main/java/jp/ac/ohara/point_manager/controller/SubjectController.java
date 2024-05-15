package jp.ac.ohara.point_manager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
import jp.ac.ohara.point_manager.model.TeacherModel;
import jp.ac.ohara.point_manager.service.SubjectService;




@Controller
public class SubjectController {
	
	@Autowired
	private SubjectService subjectService;

	
	

	// 科目登録ページ
	@GetMapping("/subject/")
	public ModelAndView add(@AuthenticationPrincipal TeacherModel teachermodel, Model model1, SubjectModel subject, ModelAndView model) {
		  model.addObject("subject", subject); 
		  model.setViewName("subject");
		  model.addObject("user2",teachermodel);


		  return model;
	}
	
	//Form送信
	@PostMapping("/subject/")
	public String subject(@Validated @ModelAttribute @NonNull SubjectModel subject ,RedirectAttributes result, ModelAndView model,
	        RedirectAttributes redirectAttributes, @AuthenticationPrincipal  TeacherModel user) {
		System.out.println("登録完了");
		System.out.println(subject);
	    try {
	    	subject.setSchoolCd(user.getSchoolCd());
	        this.subjectService.save(subject);
	        redirectAttributes.addFlashAttribute("exception", "");

	    } catch (Exception e) {
	        redirectAttributes.addFlashAttribute("exception", e.getMessage());
	    }
	    return "redirect:/subjectlist/";

	  }
	

	//科目詳細表示
	@GetMapping("/subjectlist/")
	public String add3(@AuthenticationPrincipal TeacherModel teachermodel,Model model) {
	  System.out.println(subjectService.getSubjectList().toString());
	    model.addAttribute("subjectList", subjectService.getSubjectList());
	    model.addAttribute("user2",teachermodel);


	    return "subjectlist";
	}

	// 科目情報の削除
	@PostMapping("/subject/delete/{id}")
	public String deleteSubject(@PathVariable Long id) {
	    subjectService.delete(id);
	    	return "redirect:/subjectlist/";
	}
		
		// 科目情報の編集ページ
	@GetMapping("/subjectlist/edit/{id}")
	public String editSubject(@AuthenticationPrincipal TeacherModel teachermodel,@PathVariable Long id, Model model) {
	    SubjectModel subject = subjectService.getSubjectById(id);
	    model.addAttribute("subject", subject);
	    model.addAttribute("user2",teachermodel);


	    return "edit_subject";

	}
	
    // 科目情報の更新
    @PostMapping("/subjectlist/update/{id}")
    public String updateSubject(@PathVariable Long id, @Validated @ModelAttribute @NonNull SubjectModel subject, RedirectAttributes redirectAttributes,@AuthenticationPrincipal TeacherModel user) {
        subject.setId(id);
        try {
        	subject.setSchoolCd(user.getSchoolCd());
            subjectService.saveOrUpdateSubject(subject);
            redirectAttributes.addFlashAttribute("exception", "");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("exception", e.getMessage());
        }
        


        return "redirect:/subjectfini/";
    }
    
	@GetMapping("/subjectfini/")
	public String add(@AuthenticationPrincipal TeacherModel teachermodel,Model model) {
	    model.addAttribute("user2",teachermodel);
	    return "subjectfini";
	}
   
}
