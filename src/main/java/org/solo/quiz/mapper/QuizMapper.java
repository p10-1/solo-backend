package org.solo.quiz.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.solo.quiz.domain.QuizVO;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface QuizMapper {

    // 퀴즈의 전체 개수 불러오는 메서드
    int totalCnt();

    // 퀴즈를 생성해주는 메서드
    QuizVO createQuiz(@Param("quizNo") int randomNumber);

    // 이미 포인트를 받았는지 체크하는 메서드
    int checkPoint(@Param("userId") String userId);

    // 포인트를 지급하는 메서드
    void addPoint(@Param("userId") String userId);

    // 오늘 포인트를 받았다고 수정해주는 메서드
    void checkToday(@Param("userId") String userId);

    // 매일 퀴즈로 포인트를 받았는지 여부를 리셋해주는 메서드
    void reset();
}
