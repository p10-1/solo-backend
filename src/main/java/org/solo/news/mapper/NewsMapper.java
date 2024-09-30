package org.solo.news.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.solo.news.domain.NewsVO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface NewsMapper {
    void insertNews(List<NewsVO> newsList);
    List<NewsVO> getNewsALL();
    NewsVO getNewsByNo(int no);
}
