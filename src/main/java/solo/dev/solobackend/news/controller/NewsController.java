package solo.dev.solobackend.news.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class NewsController {
    @GetMapping("/newsPage")
    public String assetPage() {
        return "newsPage";
    }


}
