package com.project.reviewservice.controller.home;

import com.project.reviewservice.domain.user.User;
import com.project.reviewservice.service.review.ReviewService;
import com.project.reviewservice.service.user.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class HomeViewController {

    @Autowired
    UserService userService;

    @Autowired
    ReviewService reviewService;

    @GetMapping("/")  //홈페이지 접근 시 로그인으로 유도
    public String home(HttpSession session , Model model) {

        //세션 여부 확인 -> 세션 없으면 login, 있으면 main
        if(session.getAttribute("username") == null) {
            return "/login";
        }

        model.addAttribute("username" , session.getAttribute("username"));
        return "main";
    }

    @GetMapping("/register") //회원가입 페이지
    public String register(HttpSession session, RedirectAttributes redirectAttributes) {

        //세션 여부 확인 -> 세션 없으면 login, 있으면 main
        if(session.getAttribute("username") == null) {
            return "/register";
        }
        // 리다이렉트 시 데이터를 전달
        // Model을 써도 redirect를 할 경우 해당 Model을 쓸 수가 없음
        // 그래서 RedirectAttributes를 활용하면 Redirect되는 사이트에 대해서도 값을 줄 수 있음
        redirectAttributes.addFlashAttribute("username", session.getAttribute("username"));
        redirectAttributes.addFlashAttribute("userId", session.getAttribute("userId"));


        return "redirect:/main";
    }

    @GetMapping("/login")
    public String login(HttpSession session , RedirectAttributes redirectAttributes) {
        //세션 여부 확인 -> 세션 없으면 login, 있으면 main
        if(session.getAttribute("username") == null) {
            return "/login";
        }

        redirectAttributes.addFlashAttribute("username", session.getAttribute("username"));
        redirectAttributes.addFlashAttribute("userId", session.getAttribute("userId"));
        return "redirect:/main";
    }

    @GetMapping("/main")
    public String main(HttpSession session , Model model) {

        if(session.getAttribute("username") == null) {
            return "/login";
        }
        model.addAttribute("username" , session.getAttribute("username"));
        model.addAttribute("userId", session.getAttribute("userId"));
        model.addAttribute("reviews", reviewService.findAllReviews());
        return "main";
    }

    @PostMapping("/login-process") //로그인 시도
    public String login(@RequestParam(name="userId") String userId
                      , @RequestParam(name="password") String password
                      , HttpSession session , Model model , RedirectAttributes redirectAttributes) {

        User user = userService.login(userId , password);

        if(user != null) {

            if(user.getUserId().equals("ADMIN")) {
                session.setAttribute("username" , "admin") ; //사용자 이름을 세션에 저장
            } else {
                session.setAttribute("username" , user.getName()) ; //사용자 이름을 세션에 저장
            }
            session.setAttribute("userId" , user.getUserId()) ; //사용자 이름을 세션에 저장

            redirectAttributes.addFlashAttribute("username" , session.getAttribute("username"));
            redirectAttributes.addFlashAttribute("userId" , session.getAttribute("userId"));

            return "redirect:/main";
        }

        model.addAttribute("error" , "존재하지 않는 사용자입니다.");
        return "login";
    }

    @PostMapping("/register")
    public String join(@ModelAttribute User user) {

        userService.join(user);

        return "redirect:/login";
    }

    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }


    @GetMapping("/main/custom-paging")
    public String mainPageUsingPaging() {

        return "custom-paging";
    }

}
