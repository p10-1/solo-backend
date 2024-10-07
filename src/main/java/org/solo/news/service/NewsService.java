package org.solo.news.service;

import org.solo.common.pagination.PageRequest;
import org.solo.news.domain.NewsVO;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface NewsService {
    List<NewsVO> fetchNews(String rssUrl);
    void insertNews(List<NewsVO> newsList);


    List<NewsVO> getNewsBycategory(PageRequest pageRequest, String category);
    int getNewsCountBycategory(String category);
    List<NewsVO> getNewsByPage(PageRequest pageRequest);
    int getNewsCount();

    Map<String, List<NewsVO>> getTodayNews(LocalDate date);
}
