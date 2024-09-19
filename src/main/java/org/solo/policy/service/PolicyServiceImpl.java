package org.solo.policy.service;

import org.solo.policy.mapper.PolicyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PolicyServiceImpl implements PolicyService {
    private final PolicyMapper policyMapper;

    @Autowired
    public PolicyServiceImpl(PolicyMapper policyMapper) {
        this.policyMapper = policyMapper;
    }
}
