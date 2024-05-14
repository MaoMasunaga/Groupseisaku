package jp.ac.ohara.point_manager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import io.micrometer.common.lang.NonNull;
import jp.ac.ohara.point_manager.model.TeacherModel;
import jp.ac.ohara.point_manager.service.TeacherService;

@Controller
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // 先生登録ページの表示
    @GetMapping("/teacher/")
    public String showTeacherForm(@AuthenticationPrincipal TeacherModel teachermodel, Model model) {
        model.addAttribute("teacher", new TeacherModel());
        model.addAttribute("user2",teachermodel);

        return "teacher";
    }
    

    // 先生登録フォームの処理
    @PostMapping("/teacher/")
    public String registerTeacher(@Validated @ModelAttribute @NonNull TeacherModel teacher, RedirectAttributes redirectAttributes) {
        try {
            // パスワードをハッシュ化してセット
        	teacher.setSchoolCd("AAA");
            String hashedPassword = passwordEncoder.encode(teacher.getPassword());
            teacher.setPassword(hashedPassword);

            teacherService.save(teacher);
            redirectAttributes.addFlashAttribute("successMessage", "先生の登録が完了しました。");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "先生の登録中にエラーが発生しました。");
        }
        return "redirect:/";
    }

    // 先生リストの表示
    @GetMapping("/teacherlist/")
    public String showTeacherList(@AuthenticationPrincipal TeacherModel teachermodel,Model model) {
        model.addAttribute("teacherList", teacherService.getTeacherList());
        model.addAttribute("user2",teachermodel);
    
        return "teacherlist";
    }

    // 先生情報の削除
    @PostMapping("/teacher/delete/{id}")
    public String deleteTeacher(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        teacherService.delete(id);
        redirectAttributes.addFlashAttribute("successMessage", "先生の削除が完了しました。");
        return "redirect:/teacherlist/";
    }

    // 先生情報の編集ページの表示
    @GetMapping("/teacherlist/edit/{id}")
    public String showEditTeacherForm(@AuthenticationPrincipal TeacherModel teachermodel, Model model1, @PathVariable Long id, Model model) {
        TeacherModel teacher = teacherService.getTeacherById(id);
        model.addAttribute("teacher", teacher);
        model.addAttribute("user2",teachermodel);

        return "edit_teacher";
    }
}
