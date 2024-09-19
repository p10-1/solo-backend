package org.solo.news.controller;

//import org.solo.news.domain.newsVO;
//import org.solo.news.service.NewsService;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class NewsController {

//    private final NewsService newsService;
//
//    @Autowired
//    public NewsController(NewsService newsService) {
//        this.newsService = newsService;
//    }

    @GetMapping("/newsPage")
    public String newsPage(Model model) {
//        List<newsVO> newsList = newsService.getAllNews(); // 뉴스 목록 가져오기
//        model.addAttribute("newsList", newsList); // 모델에 뉴스 목록 추가
        return "newsPage"; // newsPage.jsp로 이동
    }
}
