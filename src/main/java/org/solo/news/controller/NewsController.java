package org.solo.news.controller;

import org.solo.news.domain.NewsVO;
import org.solo.news.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

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

    @GetMapping("/fetch")
    public ResponseEntity<List<NewsVO>> fetchNews() {
//        System.out.println("news fetch 요청 들어옴 (controller)");
        List<NewsVO> newsList = newsService.getNewsALL(); // 데이터베이스에서 저장된 뉴스 가져오기
        return ResponseEntity.ok(newsList); // 가져온 뉴스 목록 반환
    }

}
