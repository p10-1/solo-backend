package org.solo.news.mapper;

import org.solo.news.domain.newsVO;
import java.util.List;

public interface NewsMapper {
    List<newsVO> getAllNews(); // 모든 뉴스 조회
}
