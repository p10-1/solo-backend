package org.solo.policy.scheduler;

import org.solo.policy.controller.PolicyController;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class PolicyScheduler {
    private final PolicyController policyController;
    private final TaskScheduler taskScheduler;

    public PolicyScheduler(PolicyController policyController, TaskScheduler taskScheduler) {
        this.policyController = policyController;
        this.taskScheduler = taskScheduler;
    }

    @PostConstruct
    public void scheduleFetchPolicies(){
        System.out.println("scheduling fetch policies");
        taskScheduler.scheduleAtFixedRate(() -> {
            policyController.fetchPolicies();
            System.out.println("Fetched policies at: " + System.currentTimeMillis());
        }, 60 * 60 * 1000);
    }
}
