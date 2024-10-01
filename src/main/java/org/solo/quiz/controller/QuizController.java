package org.solo.quiz.controller;

import org.solo.quiz.domain.QuizVO;
import org.solo.quiz.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/quiz")
public class QuizController {

    private final QuizService quizService;

    @Autowired
    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    @GetMapping("/create")
    public ResponseEntity<QuizVO> createQuiz() {
        QuizVO quizVO = quizService.createQuiz();
        return ResponseEntity.ok(quizVO);
    }
}
