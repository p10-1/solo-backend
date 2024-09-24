package org.solo.policy.service;

import org.solo.common.pagination.PageRequest;
import org.solo.policy.domain.PolicyVO;

import java.util.List;

public interface PolicyService {
    void fetchPolicies();
    void savePolicies(List<PolicyVO> policies);
    int getTotalCnt();
    int getTotalCntByKeyword(String keyword);
    List<PolicyVO> getPoliciesByPage(PageRequest pageRequest);
    List<PolicyVO> getPoliciesByPageAndKeyword(PageRequest pageRequest, String keyword);
}
