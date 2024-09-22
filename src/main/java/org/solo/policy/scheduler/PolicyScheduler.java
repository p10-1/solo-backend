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
        }, 24* 60 * 60 * 1000);
    }
}
