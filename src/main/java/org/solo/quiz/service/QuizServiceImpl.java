package org.solo.quiz.service;


import org.solo.quiz.domain.QuizVO;
import org.solo.quiz.mapper.QuizMapper;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class QuizServiceImpl implements QuizService {
    private final QuizMapper quizMapper;
    private int qn;

    public QuizServiceImpl(QuizMapper quizMapper) {
        this.quizMapper = quizMapper;
    }

    @Override
    public void randomNumber() {
        int totalCnt = quizMapper.totalCnt();
        qn = (int)(Math.random() * totalCnt) + 1;
    }

    @Override
    public QuizVO createQuiz() {
        int totalCnt = quizMapper.totalCnt();
        int quizNo = (int)(Math.random() * totalCnt) + 1;
        return quizMapper.createQuiz(37);
    }

    @Override
    public boolean checkPoint(String userId) {
        return quizMapper.checkPoint(userId) == 1;
    }

    @Override
    public void checkToday(String userId) {
        quizMapper.checkToday(userId);
    }

    @Override
    public void addPoint(String userId) {
        quizMapper.addPoint(userId);
    }

    @Override
    public void resetToday() {
        quizMapper.reset();
    }

}