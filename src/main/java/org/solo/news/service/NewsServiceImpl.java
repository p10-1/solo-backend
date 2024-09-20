package org.solo.news.service;

import org.solo.news.domain.NewsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.solo.news.mapper.NewsMapper;

import java.util.List;

@Service
@Transactional
public class NewsServiceImpl implements NewsService {

    private final NewsMapper newsMapper;

    @Autowired
    public NewsServiceImpl(NewsMapper newsMapper) {
        this.newsMapper = newsMapper;
    }

    @Override
    public void insertNews(NewsVO newsVO){
        newsMapper.insertNews(newsVO);
    }

    @Override
    public List<NewsVO> getNewsALL(){
        return newsMapper.getNewsALL();
    }

}
