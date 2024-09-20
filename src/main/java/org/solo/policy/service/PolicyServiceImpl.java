package org.solo.policy.service;

import org.solo.policy.domain.PolicyVO;
import org.solo.policy.mapper.PolicyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class PolicyServiceImpl implements PolicyService {
    private final PolicyMapper policyMapper;

    @Autowired
    public PolicyServiceImpl(PolicyMapper policyMapper) {
        this.policyMapper = policyMapper;
    }

    public void fetchPolicies(List<PolicyVO> policies) {
        for (PolicyVO policyVO : policies) {
            Map<String, Object> policyToken = new HashMap<>();
            policyToken.put("bizId", policyVO.getBizId());
            policyToken.put("sporCn", policyVO.getSporCn());
            if (policyMapper.findByToken(policyToken) == 0) {
                policyMapper.fetchPolicies(policyVO);
            }
        }
    }

    public int getTotalCnt() {
        return policyMapper.getTotalCnt();
    }

    public int getTotalCntByKeyword(String keyword) {
        return policyMapper.getTotalCntByKeyword(keyword);
    }

    public List<PolicyVO> getPoliciesByPage(int page, int pageSize) {
        return policyMapper.getPoliciesByPage((page-1) * pageSize, pageSize);
    }

    public List<PolicyVO> getPoliciesByPageAndKeyword(int page, int pageSize, String keyword) {
        int offset = (page - 1) * pageSize;
        Map<String, Object> params = new HashMap<>();
        params.put("offset", offset);
        params.put("limit", pageSize);
        params.put("keyword", keyword);

        return policyMapper.getPoliciesByPageAndKeyword(params);
    }
}
