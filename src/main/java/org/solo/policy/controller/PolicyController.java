package org.solo.policy.controller;

import org.solo.policy.domain.PolicyVO;
import org.solo.policy.service.PolicyService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/policy")
public class PolicyController {

    private final PolicyService policyService;

    @Value("${policyAPI.url}") String url;
    @Value("${policyAPI.openApiVlak}") String openApiVlak;
    @Value("${policyAPI.display}") String display;

    public PolicyController(PolicyService policyService) {
        this.policyService = policyService;
    }

    @GetMapping("/list")
    public ResponseEntity<Map<String, Object>> getPolicies(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(required = false) String keyword) {
        int pageSize = 10;

        List<PolicyVO> policies = (keyword != null && !keyword.isEmpty())
                ? policyService.getPoliciesByPageAndKeyword(page, pageSize, keyword)
                : policyService.getPoliciesByPage(page, pageSize);

        int totalPoliciesCount = (keyword != null && !keyword.isEmpty())
                ? policyService.getTotalCntByKeyword(keyword)
                : policyService.getTotalCnt();
        int totalPages = (int) Math.ceil((double) totalPoliciesCount / pageSize);

//        System.out.println("currentPage: " + page + ", totalPages: " + totalPages + "정책 리스트 수: "+ policies.size());
        Map<String, Object> response = new HashMap<>();
        response.put("policies", policies); // 정책 리스트
        response.put("totalPages", totalPages); // 총 페이지 수
        response.put("currentPage", page); // 현재 페이지
        response.put("totalPoliciesCount", totalPoliciesCount); // 총 정책 수

        return ResponseEntity.ok(response);
    }

    @GetMapping("/fetch")
    public ResponseEntity<Void> fetchPolicies() {
        policyService.fetchPolicies();
        return ResponseEntity.ok().build();
    }
}
