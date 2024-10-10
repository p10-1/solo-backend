package org.solo.news.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.solo.news.domain.NewsVO;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Mapper
@Repository
public interface NewsMapper {

    void insertNews(List<NewsVO> newsList);
    NewsVO getNewsByNo(int no);
    List<NewsVO> getNewsBycategory(@Param("offset") int offset, @Param("limit") int limit, @Param("category") String category);
    int getNewsCountBycategory(@Param("category") String category);
    List<NewsVO> getNewsByPage(@Param("offset") int offset, @Param("limit") int limit);
    int getNewsCount();
    //List<NewsVO> getTodayNews(LocalDate date);
    List<NewsVO> getTodayNews();




}
