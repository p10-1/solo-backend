package org.solo.news.scheduler;

import org.solo.news.domain.NewsVO;
import org.solo.news.service.NewsService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
public class NewsScheduler {
    private final NewsService newsService;
    private final TaskScheduler taskScheduler;

    @Value("${newsAPI.rssUrl1}") String rssUrl1;
    @Value("${newsAPI.rssUrl2}") String rssUrl2;
    @Value("${newsAPI.rssUrl3}") String rssUrl3;

    public NewsScheduler(NewsService newsService, TaskScheduler taskScheduler) {
        this.newsService = newsService;
        this.taskScheduler = taskScheduler;
    }

    @PostConstruct
    public void scheduleFetchNews(){
        System.out.println("scheduling fetch news");
        taskScheduler.scheduleAtFixedRate(() -> {
            try {
                List<NewsVO> newsList1 = newsService.fetchNews(rssUrl1);
                List<NewsVO> newsList2 = newsService.fetchNews(rssUrl2);
                List<NewsVO> newsList3 = newsService.fetchNews(rssUrl3);

                // 뉴스 리스트 합치기 및 DB에 저장
                List<NewsVO> combinedNewsList = new ArrayList<>();
                combinedNewsList.addAll(newsList1);
                combinedNewsList.addAll(newsList2);
                combinedNewsList.addAll(newsList3);

                newsService.insertNews(combinedNewsList);
                System.out.println("Fetched news at: " + System.currentTimeMillis());
            } catch (Exception e) {
                System.err.println("Error fetching news: " + e.getMessage());
            }
        }, 60 * 60 * 1000); // 1시간마다 실행
    }
}
