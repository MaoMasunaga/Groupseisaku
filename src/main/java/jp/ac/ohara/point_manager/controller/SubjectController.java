package jp.ac.ohara.point_manager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

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
}
