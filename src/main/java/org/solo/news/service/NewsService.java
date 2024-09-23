package org.solo.news.service;

import org.solo.news.domain.NewsVO;

import java.util.List;

public interface NewsService {
    void insertNews(NewsVO newsVO);
    List<NewsVO> getNewsALL();
}
