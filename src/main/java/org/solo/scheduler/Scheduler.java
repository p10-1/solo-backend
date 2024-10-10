package org.solo.scheduler;

import org.solo.board.service.BoardService;
import org.solo.news.domain.NewsVO;
import org.solo.news.service.NewsService;
import org.solo.policy.service.PolicyService;
import org.solo.product.service.ProductService;
import org.solo.quiz.service.QuizService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class Scheduler {
    private final ProductService productService;
    private final PolicyService policyService;
    private final NewsService newsService;
    private final QuizService quizService;
    private final BoardService boardService;

    public Scheduler(ProductService productService, PolicyService policyService,
                     NewsService newsService, QuizService quizService, BoardService boardService) {
        this.productService = productService;
        this.policyService = policyService;
        this.newsService = newsService;
        this.quizService = quizService;
        this.boardService = boardService;
    }
    @Scheduled(cron = "0 * * * * *", zone = "Asia/Seoul")
    public void doEveryHour() {
        productService.fetchDeposit();
        productService.fetchSaving();
        productService.fetchLoan();
        policyService.fetchPolicies();
    }

    // 매일 수행되는 작업
    @Scheduled(cron = "0 0 0 * * *", zone = "Asia/Seoul")
    public void doEveryDay() {
        List<NewsVO> combinedNewsList = newsService.fetchAllNews();
        newsService.insertNews(combinedNewsList);
        quizService.resetToday();
    }

    @Scheduled(cron = "0 0 0 1 * *", zone = "Asia/Seoul")
    public void doEveryMonth() {
        boardService.bestBoardPointUp();
    }


}
