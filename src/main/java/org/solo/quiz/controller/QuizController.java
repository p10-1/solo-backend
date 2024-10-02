package org.solo.quiz.controller;

import org.solo.quiz.domain.QuizVO;
import org.solo.quiz.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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

    @PostMapping("/point")
    public ResponseEntity<String> pointQuiz(@RequestBody Map<String, String> item) {
        String userId = item.get("userId");
        if (quizService.checkPoint(userId)){ // 이미 포인트를 받은 유저
            return ResponseEntity.ok("이미 포인트를 받았습니다.");
        } else {
            quizService.addPoint(userId);
            quizService.checkToday(userId);
            return ResponseEntity.ok("10P가 적립되었습니다. ");
        }
    }
}
