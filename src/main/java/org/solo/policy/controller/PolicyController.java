package org.solo.policy.controller;

import org.solo.common.pagination.Page;
import org.solo.common.pagination.PageRequest;
import org.solo.policy.domain.PolicyVO;
import org.solo.policy.service.PolicyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/policy")
public class PolicyController {

    private final PolicyService policyService;

    public PolicyController(PolicyService policyService) {
        this.policyService = policyService;
    }

    // 정책 리스트 보여주는 API
    @GetMapping("/list")
    public ResponseEntity<Page<PolicyVO>> getPolicies(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int amount,
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "0") String category) {
        System.out.println("keyword: " + keyword + ", category: " + category);
        PageRequest pageRequest = PageRequest.of(page, amount);

        List<PolicyVO> policies = (keyword != null && !keyword.isEmpty())
                ? policyService.getPoliciesByPageAndKeyword(pageRequest, keyword, category)
                : policyService.getPoliciesByPage(pageRequest,category);

        int totalPoliciesCount = (keyword != null && !keyword.isEmpty())
                ? policyService.getTotalCntByKeyword(keyword, category)
                : policyService.getTotalCnt(category);
        System.out.println("totalPoliciesCount: " + totalPoliciesCount);
        Page<PolicyVO> policiesPage = Page.of(pageRequest, totalPoliciesCount, policies);

        return ResponseEntity.ok(policiesPage);
    }

    // openAPI 정책을 불러오는 API
    @GetMapping("/fetch")
    public ResponseEntity<Void> fetchPolicies() {
        policyService.fetchPolicies();
        return ResponseEntity.ok().build();
    }

    // 청년 정책을 추천해주는 API
    @GetMapping("/recommend")
    public ResponseEntity<List<PolicyVO>> recommendPolicies() {
        List<PolicyVO> recommends = policyService.recommendPolicies();
        return ResponseEntity.ok(recommends);
    }
}
