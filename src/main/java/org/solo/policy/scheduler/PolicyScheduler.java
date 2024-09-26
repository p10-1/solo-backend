package org.solo.policy.scheduler;

import org.solo.policy.controller.PolicyController;
import org.solo.policy.service.PolicyService;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class PolicyScheduler {
    private final PolicyService policyService;
    private final TaskScheduler taskScheduler;

    public PolicyScheduler(PolicyService policyService, TaskScheduler taskScheduler) {
        this.policyService = policyService;
        this.taskScheduler = taskScheduler;
    }

    @PostConstruct
    public void scheduleFetchPolicies(){
        System.out.println("scheduling fetch policies");
        taskScheduler.scheduleAtFixedRate(() -> {
            policyService.fetchPolicies();
            System.out.println("Fetched policies at: " + System.currentTimeMillis());
        },  60 * 60 * 1000); // 1시간에 한번 패치
        // 처음 DB를 생성하고서는 프로퍼티에 policyAPI.display=100 , policyAPI.pageIndex=5 로 설정하고 서버 실행 후 다시 10,1로 바꾸고 다시 실행

    }
}
