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
@RequestMapping("/api/policies")
public class PolicyController {

    private final PolicyService policyService;

    @Value("${policyAPI.url}") String url;
    @Value("${policyAPI.openApiVlak}") String openApiVlak;
    @Value("${policyAPI.display}") String display;

    public PolicyController(PolicyService policyService) {
        this.policyService = policyService;
    }

    @GetMapping
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

        Map<String, Object> response = new HashMap<>();
        response.put("policies", policies);
        response.put("totalPages", totalPages);
        response.put("currentPage", page);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/fetch")
    public ResponseEntity<Void> fetchPolicies() {
        policyService.fetchPolicies();
        return ResponseEntity.ok().build();
    }
}
