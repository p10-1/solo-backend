package org.solo.news.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.solo.news.domain.NewsVO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface NewsMapper {
    void insertNews(NewsVO newsVO);
    List<NewsVO> getNewsALL();
}
