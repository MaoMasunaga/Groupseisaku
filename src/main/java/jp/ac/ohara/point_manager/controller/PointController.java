package jp.ac.ohara.point_manager.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jp.ac.ohara.point_manager.model.StudentModel;
import jp.ac.ohara.point_manager.model.SubjectModel;
import jp.ac.ohara.point_manager.model.TeacherModel;
import jp.ac.ohara.point_manager.model.TestModel;
import jp.ac.ohara.point_manager.repository.StudentRepository;
import jp.ac.ohara.point_manager.repository.SubjectRepository;
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
    private SubjectRepository subjectRepository;
    @Autowired
    private SubjectService subjectService;
    @Autowired
    private StudentService studentService;

    @GetMapping("/point/")
    public String getAllStudents(Model model, @AuthenticationPrincipal TeacherModel teacher) {
        if (teacher == null) {
            // ユーザーが認証されていない場合の処理
            return "redirect:/login";
        }

        String schoolCd = teacher.getSchoolCd();
        List<StudentModel> studentList = studentService.getStudentEntYear(schoolCd);
        List<SubjectModel> subjectList = subjectService.getAllSubjectBySchoolCd(schoolCd);
        List<TestModel> tests = testService.getAllStudentsBySchoolCd(schoolCd);

        model.addAttribute("user", teacher); // userとしてテンプレートに追加
        model.addAttribute("studentList", studentList);
        model.addAttribute("subjectList", subjectList);
        model.addAttribute("tests", tests);
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
        IntStream.rangeClosed(1, 2).forEach(no::add);
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
            Model model,
            @AuthenticationPrincipal TeacherModel teacher) {

        if (teacher == null) {
            return "redirect:/login";
        }

        String schoolCd = teacher.getSchoolCd();

        // 条件がnullでない場合のみ検索に使用する
        List<StudentModel> students;
        if (entYear != null || classNum != null) {
            students = studentService.searchStudents2(entYear, classNum);
        } else {
            // 全ての条件がnullの場合は全ての学生を表示
            students = studentService.getStudentList();
        }

        List<SubjectModel> subjectList = subjectService.getAllSubjectBySchoolCd(schoolCd);

        model.addAttribute("user", teacher); // userとしてテンプレートに追加
        model.addAttribute("studentList", students);
        model.addAttribute("subjectList", subjectList);

        return "point";
    }


    
    @PostMapping("/point/")
    public String registerPoint(
            @RequestParam("studentNo") String studentNo,
            @RequestParam("subjectCd") String subjectCd,
            @RequestParam("no") int no,
            @RequestParam("point") int point,
            @RequestParam("classNum") String classNum,
            RedirectAttributes redirectAttributes,
            @AuthenticationPrincipal TeacherModel user) {

        // 学生番号を元に学生情報を取得
        StudentModel student = studentRepository.findByNo(studentNo);

        // 学生情報が存在する場合、点数を登録
        if (student != null) {
            TestModel testModel = new TestModel();
            testModel.setStudent(student);
            testModel.setSubjectCd(subjectCd);
            testModel.setSchoolCd(user.getSchoolCd());
            testModel.setNo(no);
            testModel.setPoint(point);
            testModel.setClassNum(classNum);

            try {
            	System.out.println(testModel);
                testRepository.save(testModel);
                redirectAttributes.addFlashAttribute("success", "登録が完了しました");
            } catch (DataIntegrityViolationException e) {
                redirectAttributes.addFlashAttribute("error", "重複エントリが存在します");
            }
        } else {
            redirectAttributes.addFlashAttribute("error", "学生が見つかりません");
        }

        return "redirect:/point/";
    }

}


//package jp.ac.ohara.point_manager.controller;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.stream.IntStream;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.dao.DataIntegrityViolationException;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//
//import jp.ac.ohara.point_manager.model.StudentModel;
//import jp.ac.ohara.point_manager.model.StudentPoint;
//import jp.ac.ohara.point_manager.model.StudentPointsWrapper;
//import jp.ac.ohara.point_manager.model.SubjectModel;
//import jp.ac.ohara.point_manager.model.TeacherModel;
//import jp.ac.ohara.point_manager.model.TestModel;
//import jp.ac.ohara.point_manager.service.StudentService;
//import jp.ac.ohara.point_manager.service.SubjectService;
//import jp.ac.ohara.point_manager.service.TestService;
//
//@Controller
//public class PointController {
//
//    @Autowired
//    private StudentService studentService;
//    @Autowired
//    private TestService testService;
//    @Autowired
//    private SubjectService subjectService;
//    
//    @GetMapping("/point/")
//    public String getAllStudents(Model model, @AuthenticationPrincipal TeacherModel teacher) {
//        if (teacher == null) {
//            return "redirect:/login";
//        }
//
//        String schoolCd = teacher.getSchoolCd();
//        List<StudentModel> studentList = studentService.getStudentEntYear(schoolCd);
//        List<SubjectModel> subjectList = subjectService.getAllSubjectBySchoolCd(schoolCd);
//        model.addAttribute("studentList", studentList);
//        model.addAttribute("subjectList", subjectList);
//        model.addAttribute("studentPointsWrapper", new StudentPointsWrapper()); // 追加
//        return "point";
//    }
//
//
//  @ModelAttribute("subjects")
//  public List<String> populateSubjects() {
//      // subjecttableから科目名を取得してリストとして返す
//      return subjectService.getAllSubjects();
//  }
//  @ModelAttribute("years")
//  public List<Integer> getYears() {
//      List<Integer> years = new ArrayList<>();
//      IntStream.rangeClosed(2014, 2034).forEach(years::add);
//      return years;
//  }
//
//  @ModelAttribute("no")
//  public List<Integer> getNo() {
//      List<Integer> no = new ArrayList<>();
//      IntStream.rangeClosed(1, 2).forEach(no::add);
//      return no;
//  }
//
//  @ModelAttribute("classNumbers")
//  public List<String> getClassNumbers() {
//      List<String> classNumbers = new ArrayList<>();
//      for (int i = 1; i <= 3; i++) {
//          for (int j = 1; j <= 5; j++) {
//              classNumbers.add(i + "0" + j);
//          }
//      }
//      return classNumbers;
//  }
//
//  @GetMapping("/point/search")
//  public String searchStudents(
//          @RequestParam(value = "entYear", required = false) Integer entYear,
//          @RequestParam(value = "classNum", required = false) String classNum,
//          Model model,
//          @AuthenticationPrincipal TeacherModel teacher) {
//
//      if (teacher == null) {
//          return "redirect:/login";
//      }
//
//      String schoolCd = teacher.getSchoolCd();
//      List<StudentModel> students = studentService.searchStudents2(entYear, classNum);
//      List<SubjectModel> subjectList = subjectService.getAllSubjectBySchoolCd(schoolCd);
//
//      model.addAttribute("studentList", students);
//      model.addAttribute("subjectList", subjectList);
//      model.addAttribute("studentPointsWrapper", new StudentPointsWrapper()); // 追加
//
//      return "point";
//  }
//
//
//    @PostMapping("/point/")
//    public String registerPoints(
//            @RequestParam("subjectCd") String subjectCd,
//            @RequestParam("no") int no,
//            @ModelAttribute StudentPointsWrapper studentPointsWrapper,
//            RedirectAttributes redirectAttributes,
//            @AuthenticationPrincipal UserDetails userDetails) {
//
//        String username = userDetails.getUsername();
//        TeacherModel teacher = findTeacherByUsername(username); // 仮に、TeacherModelをユーザーネームから取得する方法
//
//        boolean success = true;
//        for (StudentPoint studentPoint : studentPointsWrapper.getStudents()) {
//            StudentModel student = studentService.findByNo(studentPoint.getStudentNo());
//
//            if (student != null) {
//                TestModel testModel = new TestModel();
//                testModel.setStudent(student);
//                testModel.setSubjectCd(subjectCd);
//                testModel.setSchoolCd(teacher.getSchoolCd());
//                testModel.setNo(no);
//                testModel.setPoint(studentPoint.getPoint());
//                testModel.setClassNum(studentPoint.getClassNum());
//
//                try {
//                    testService.saveTest(testModel);
//                } catch (DataIntegrityViolationException e) {
//                    success = false;
//                }
//            } else {
//                success = false;
//            }
//        }
//
//        if (success) {
//            redirectAttributes.addFlashAttribute("success", "登録が完了しました");
//        } else {
//            redirectAttributes.addFlashAttribute("error", "登録に失敗しました");
//        }
//
//        return "redirect:/point/";
//    }
//
//    // 仮のメソッド
//    private TeacherModel findTeacherByUsername(String username) {
//        // usernameを使用してTeacherModelを取得する実装をここに追加します
//        return new TeacherModel(); // 例
//    }
//
//    // 他のコントローラメソッドも含む
//    // ...
//}
