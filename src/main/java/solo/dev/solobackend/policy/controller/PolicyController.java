package solo.dev.solobackend.policy.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PolicyController {
    @GetMapping("/policyPage")
    public String policy() {
        return "policyPage";
    }


}
