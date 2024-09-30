package org.solo.quiz.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.solo.quiz.domain.QuizVO;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface QuizMapper {
//    int findByTerm(@Param("fnceDictNm") String fnceDictNm);
//    void fetchQuizzes(QuizVO quizVO);
    int totalCnt();
    QuizVO createQuiz(@Param("quizNo") int randomNumber);
}
