package org.solo.policy.service;

import org.solo.common.pagination.PageRequest;
import org.solo.policy.domain.PolicyVO;

import java.util.List;

public interface PolicyService {
    void fetchPolicies();
    void savePolicies(List<PolicyVO> policies);
    int getTotalCnt(String category);
    int getTotalCntByKeyword(String keyword, String category);
    List<PolicyVO> getPoliciesByPage(PageRequest pageRequest, String category);
    List<PolicyVO> getPoliciesByPageAndKeyword(PageRequest pageRequest, String keyword, String category);
    List<PolicyVO> recommendPolicies();
}
