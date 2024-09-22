package org.solo.policy.service;

import org.solo.policy.domain.PolicyVO;

import java.util.List;

public interface PolicyService {
    void fetchPolicies();
    void savePolicies(List<PolicyVO> policies);
    int getTotalCnt();
    int getTotalCntByKeyword(String keyword);
    List<PolicyVO> getPoliciesByPage(int page, int pageSize);
    List<PolicyVO> getPoliciesByPageAndKeyword(int page, int pageSize, String keyword);
}
