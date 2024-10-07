package org.solo.scheduler;

import org.solo.board.service.BoardService;
import org.solo.news.service.NewsService;
import org.solo.policy.service.PolicyService;
import org.solo.product.service.ProductService;
import org.solo.quiz.service.QuizService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Scheduler {
    private final ProductService productService;
    private final PolicyService policyService;
//    private final NewsService newsService;
    private final QuizService quizService;
    private final BoardService boardService;


    public Scheduler(ProductService productService, PolicyService policyService, NewsService newsService, QuizService quizService, BoardService boardService) {
        this.productService = productService;
        this.policyService = policyService;
//        this.newsService = newsService;
        this.quizService = quizService;
        this.boardService = boardService;
    }

    // 매시간 0분과 30분에 두번 수행되는 작업
    @Scheduled(cron = "0 0,30 * * * *", zone = "Asia/Seoul")
    public void doEveryHour() {
        productService.fetchDeposit();
        productService.fetchSaving();
        policyService.fetchPolicies();
    }

    // 매일 수행되는 작업
    @Scheduled(cron = "0 0 0 * * *", zone = "Asia/Seoul")
    public void doEveryDay() {
        quizService.resetToday();
    }

    // 매달 수행되는 작업
    @Scheduled(cron = "0 0 0 1 * *", zone = "Asia/Seoul")
    public void doEveryMonth() {
        boardService.bestBoardPointUp();
    }
}
