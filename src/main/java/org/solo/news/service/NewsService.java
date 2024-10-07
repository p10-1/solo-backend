package org.solo.news.service;

import org.solo.common.pagination.PageRequest;
import org.solo.news.domain.NewsVO;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface NewsService {

    // rss 뉴스 가져옴
    List<NewsVO> fetchAllNews();
    List<NewsVO> fetchNews(String rssUrl);
    // rss 뉴스 db에 저장
    void insertNews(List<NewsVO> newsList);
    // 전체 뉴스 조회
    List<NewsVO> getNewsByPage(PageRequest pageRequest);
    // 카테고리별 뉴스 조회
    List<NewsVO> getNewsBycategory(PageRequest pageRequest, String category);
    // 카테고리 별 뉴스 개수 조회
    int getNewsCountBycategory(String category);
    // 전체 뉴스 개수 조회
    int getNewsCount();
    // 오늘의 뉴스 가져옴
    Map<String, List<NewsVO>> getTodayNews(LocalDate date);
}
