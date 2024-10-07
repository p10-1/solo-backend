package org.solo.quiz.controller;

import org.solo.quiz.domain.QuizVO;
import org.solo.quiz.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/quiz")
public class QuizController {

    private final QuizService quizService;

    @Value("${point.alreadyReceived.message}") String alreadyReceivedMessage;
    @Value("${point.earned.message}") String earnedMessage;

    @Autowired
    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    // 퀴즈를 생성하는 API
    @GetMapping("/create")
    public ResponseEntity<QuizVO> createQuiz() {
        QuizVO quizVO = quizService.createQuiz();
        return ResponseEntity.ok(quizVO);
    }

    // 퀴즈를 맞추면 point를 지급하는 API
    @PostMapping("/point")
    public ResponseEntity<String> pointQuiz(@RequestBody Map<String, String> item) {
        String userId = item.get("userId");
        if (quizService.checkPoint(userId)){
            return ResponseEntity.ok(alreadyReceivedMessage);
        } else {
            quizService.addPoint(userId);
            quizService.checkToday(userId);
            return ResponseEntity.ok(earnedMessage);
        }
    }
}
