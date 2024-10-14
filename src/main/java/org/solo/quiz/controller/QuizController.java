package org.solo.quiz.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.solo.quiz.domain.QuizVO;
import org.solo.quiz.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/quiz")
@Api(value = "Quiz Controller", tags = "금융 퀴즈 API")
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
    @ApiOperation(value = "금융퀴즈 불러오기", notes = "생성형 AI를 통해 만든 금융퀴즈 중 랜덤으로 한개 불러옵니다.")
    public ResponseEntity<QuizVO> createQuiz() {
        QuizVO quizVO = quizService.createQuiz();
        return ResponseEntity.ok(quizVO);
    }

    // 퀴즈를 맞추면 point를 지급하는 API
    @PostMapping("/point")
    @ApiOperation(value = "금융퀴즈 포인트 제공하기", notes = "하루에 한번만 금융퀴즈를 맞출 시 10포인트를 제공합니다.")
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
