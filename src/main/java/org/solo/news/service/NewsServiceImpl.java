package org.solo.news.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.solo.news.mapper.NewsMapper;

@Service
@Transactional
public class NewsServiceImpl implements NewsService {
    private final NewsMapper newsMapper;

    @Autowired
    public NewsServiceImpl(NewsMapper newsMapper) {
        this.newsMapper = newsMapper;
    }
}
