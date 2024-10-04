package org.solo.news.controller;

import org.solo.news.domain.NewsVO;
import org.solo.news.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.solo.common.pagination.Page;
import org.solo.common.pagination.PageRequest;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

//@Controller
@RestController
@RequestMapping("/api/news")
public class NewsController {

    private final NewsService newsService;

    // 생성자 주입
    @Autowired
    public NewsController(NewsService newsService) {
        this.newsService = newsService; // 생성자에서 초기화
    }


    @GetMapping("/getNews")
    public ResponseEntity<Page<NewsVO>> fetchAllNews(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int amount) {


        PageRequest pageRequest = PageRequest.of(page, amount);
        List<NewsVO> newsList = newsService.getNewsByPage(pageRequest);
        int totalNewsCount = newsService.getNewsCount();

        Page<NewsVO> newsPage = Page.of(pageRequest, totalNewsCount, newsList);


        return ResponseEntity.ok(newsPage);
    }
    @GetMapping("/getNewsBycategory")
    public ResponseEntity<Page<NewsVO>> fetchNews(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int amount,
            @RequestParam(required = false) String category) {


        // PageRequest 객체 생성
        PageRequest pageRequest = PageRequest.of(page, amount);
        // 뉴스 데이터를 가져오고 Page 객체 생성, 뉴스 개수 조회
        List<NewsVO> newsList = newsService.getNewsBycategory(pageRequest, category);
        int totalNewsCount = newsService.getNewsCountBycategory(category);

        Page<NewsVO> newsPage = Page.of(pageRequest, totalNewsCount, newsList);

        return ResponseEntity.ok(newsPage);
    }

    @GetMapping("/recommend")
    public Map<String, List<NewsVO>> getTodayNews() {
        Map<String, List<NewsVO>> result = newsService.getTodayNews(LocalDate.now());
        return result;
    }






}
