package org.solo.news.controller;

import org.solo.news.domain.NewsVO;
import org.solo.news.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.solo.common.pagination.Page;
import org.solo.common.pagination.PageRequest;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/news")
public class NewsController {

    private final NewsService newsService;

//    private static final int DEFAULT_PAGE = 1;
//    private static final int DEFAULT_AMOUNT = 10;

    @Autowired
    public NewsController(NewsService newsService) {
        this.newsService = newsService; // 생성자에서 초기화
    }

    // 페이지 객체 생성 메서드
    private Page<NewsVO> createPage(PageRequest pageRequest, List<NewsVO> newsList, int totalNewsCount) {
        return Page.of(pageRequest, totalNewsCount, newsList);
    }


    // 뉴스페이지 -> 전체뉴스
    @GetMapping("/getNews")
    public ResponseEntity<Page<NewsVO>> getAllNews(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int amount) {

        PageRequest pageRequest = PageRequest.of(page, amount);
        List<NewsVO> newsList = newsService.getNewsByPage(pageRequest);
        int totalNewsCount = newsService.getNewsCount();

        Page<NewsVO> newsPage = createPage(pageRequest, newsList, totalNewsCount);
        return ResponseEntity.ok(newsPage);
    }


    // 뉴스페이지 -> 카테고리 별 필터링
    @GetMapping("/getNewsBycategory")
    public ResponseEntity<Page<NewsVO>> getNews(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int amount,
            @RequestParam Optional<String> category) {

        PageRequest pageRequest = PageRequest.of(page, amount);
        List<NewsVO> newsList = newsService.getNewsBycategory(pageRequest, category.orElse(null));
        int totalNewsCount = newsService.getNewsCountBycategory(category.orElse(null));

        Page<NewsVO> newsPage = createPage(pageRequest, newsList, totalNewsCount);
        return ResponseEntity.ok(newsPage);
    }


    // 홈화면 -> 오늘의 뉴스 추천
    @GetMapping("/recommend")
    public ResponseEntity<Map<String, List<NewsVO>>> getTodayNews() {
        Map<String, List<NewsVO>> result = newsService.getTodayNews(LocalDate.now());
        if (result.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(result);
    }
}
