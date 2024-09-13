package solo.dev.solobackend.member.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MemberController {
    @GetMapping("/login")
    public String login() {

//        model.addAttribute("userID", userID);
        return "login";
    }
    @PostMapping("/loginAf")
    public String loginAf(@RequestParam("userID") String userID, Model model) {
        model.addAttribute("userID", userID);
        return "/index";
    }

    @GetMapping("/mypage")
    public String mypage() {
        return "myPage";
    }
}