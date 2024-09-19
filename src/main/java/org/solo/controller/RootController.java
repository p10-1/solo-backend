package org.solo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class RootController {

    @GetMapping("/")
    public String root() {
//        log.info("================> HomeController /");
//        return "index";
        log.info("=================> BoardController /");
        return "redirect:/board/list";
    }


}
