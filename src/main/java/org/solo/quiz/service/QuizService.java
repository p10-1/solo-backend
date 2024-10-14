package org.solo.quiz.service;

import org.solo.quiz.domain.QuizVO;

public interface QuizService {
    void randomNumber();
    QuizVO createQuiz();
    boolean checkPoint(String userId);
    void addPoint(String userId);
    void checkToday(String userId);
    void resetToday();
}
