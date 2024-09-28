package org.solo.news.scheduler;

import org.solo.news.domain.NewsVO;
import org.solo.news.service.NewsService;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
public class NewsScheduler {
    private final NewsService newsService;
    private final TaskScheduler taskScheduler;

    public NewsScheduler(NewsService newsService, TaskScheduler taskScheduler) {
        this.newsService = newsService;
        this.taskScheduler = taskScheduler;
    }

    @PostConstruct
    public void scheduleFetchNews(){
        System.out.println("scheduling fetch news");
        taskScheduler.scheduleAtFixedRate(() -> {
            try {
                List<NewsVO> newsList = newsService.fetchNews();
                newsService.insertNews(newsList);
                System.out.println("Fetched news at: " + System.currentTimeMillis());
            } catch (Exception e) {
                System.err.println("Error fetching news: " + e.getMessage());
            }
        }, 60 * 60 * 1000); // 1시간마다 실행
    }
}
