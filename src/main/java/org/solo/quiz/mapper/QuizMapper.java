package org.solo.quiz.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.solo.quiz.domain.QuizVO;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface QuizMapper {
    int totalCnt();
    QuizVO createQuiz(@Param("quizNo") int randomNumber);
    int checkPoint(@Param("userId") String userId);
    void addPoint(@Param("userId") String userId);
    void checkToday(@Param("userId") String userId);
    void reset();
}
