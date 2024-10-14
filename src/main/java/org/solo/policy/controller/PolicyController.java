package org.solo.policy.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api(value = "Policy Controller", tags = "청년 정책 API")
public class PolicyController {

    private final PolicyService policyService;

    public PolicyController(PolicyService policyService) {
        this.policyService = policyService;
    }

    // 정책 리스트 보여주는 API
    @GetMapping("/list")
    @ApiOperation(value = "청년 정책 불러오기", notes = "원하는 조건에 맞는 청년 정책 목록을 불러옵니다.")
    public ResponseEntity<Page<PolicyVO>> getPolicies(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int amount,
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "0") String category) {
        PageRequest pageRequest = PageRequest.of(page, amount);

        List<PolicyVO> policies = (keyword != null && !keyword.isEmpty())
                ? policyService.getPoliciesByPageAndKeyword(pageRequest, keyword, category)
                : policyService.getPoliciesByPage(pageRequest,category);

        int totalPoliciesCount = (keyword != null && !keyword.isEmpty())
                ? policyService.getTotalCntByKeyword(keyword, category)
                : policyService.getTotalCnt(category);
        Page<PolicyVO> policiesPage = Page.of(pageRequest, totalPoliciesCount, policies);

        return ResponseEntity.ok(policiesPage);
    }

    // openAPI 정책을 불러오는 API
    @GetMapping("/fetch")
    @ApiOperation(value = "청년 정책 DB에 저장하기", notes = "온통 청년 open API를 통해 청년 정책을 불러와 데이터베이스에 저장합니다.")
    public ResponseEntity<Void> fetchPolicies() {
        policyService.fetchPolicies();
        return ResponseEntity.ok().build();
    }

    // 청년 정책을 추천해주는 API
    @GetMapping("/recommend")
    @ApiOperation(value = "추천 청년 정책 목록 불러오기", notes = "데이터베이스 상단 5개의 청년 정책을 불러옵니다.")
    public ResponseEntity<List<PolicyVO>> recommendPolicies() {
        List<PolicyVO> recommends = policyService.recommendPolicies();
        return ResponseEntity.ok(recommends);
    }
}
