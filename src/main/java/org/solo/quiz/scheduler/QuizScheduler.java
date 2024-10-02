package org.solo.quiz.scheduler;

import org.solo.quiz.service.QuizService;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class QuizScheduler {
    private final QuizService quizService;
    private final TaskScheduler taskScheduler;

    public QuizScheduler(QuizService quizService, TaskScheduler taskScheduler) {
        this.quizService = quizService;
        this.taskScheduler = taskScheduler;
    }

    @PostConstruct
    public void ScheduleResetQuiz() {
        System.out.println("scheduling reset quiz");
        taskScheduler.scheduleAtFixedRate(() -> {
            quizService.resetToday();
        }, 24 * 60 * 60 * 1000);
    }
}
