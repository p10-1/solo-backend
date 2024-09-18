package org.solo.member.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
public class MemberController {

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/loginAf")
    public String loginAf(@RequestParam("userID") String userID, HttpSession session) {
        session.setAttribute("userID", userID); // 세션에 userID 저장
        return "index"; // index.jsp로 이동
    }

    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // 세션 무효화
        return "redirect:/"; // root 페이지로 리디렉션
    }

    @GetMapping("/mypage")
    public String mypage(HttpSession session, Model model) {
        String userID = (String) session.getAttribute("userID"); // 세션에서 userID 가져오기
        if (userID == null) {
            return "redirect:/login"; // 로그인 페이지로 리디렉션
        }
        model.addAttribute("userID", userID); // 사용자 이름 설정
        return "myPage"; // myPage.jsp로 이동
    }
}
