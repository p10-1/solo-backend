package org.solo.news.controller;

import io.swagger.annotations.Api;
import org.solo.news.domain.NewsVO;
import org.solo.news.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.solo.common.pagination.Page;
import org.solo.common.pagination.PageRequest;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/news")
@Api(value = "News controller", tags = "뉴스 API")
public class NewsController {

    private final NewsService newsService;


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

    // 홈화면 -> 오늘의 뉴스 추천 (최근뉴스로 보여줌)
    @GetMapping("/recommend")
    public ResponseEntity<Map<String, List<NewsVO>>> getTodayNews() {
        Map<String, List<NewsVO>> result = newsService.getTodayNews();
        if (result.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(result);
    }
}
