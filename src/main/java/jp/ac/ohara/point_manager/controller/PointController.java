package jp.ac.ohara.point_manager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import jp.ac.ohara.point_manager.model.StudentModel;
import jp.ac.ohara.point_manager.model.TestModel;
import jp.ac.ohara.point_manager.repository.StudentRepository;
import jp.ac.ohara.point_manager.repository.TestRepository;

@Controller
public class PointController {

    @Autowired
    private TestRepository testRepository;

    @Autowired
    private StudentRepository studentRepository;

 // 成績ページ
    @GetMapping("/point/")
    public ModelAndView add(@AuthenticationPrincipal UserDetails user, Model model1,TestModel test, ModelAndView model) {
        model.addObject("test", test); 
        model.setViewName("point");
        model1.addAttribute("user2",user);


        return model;
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
