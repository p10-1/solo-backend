package org.solo.policy.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.solo.policy.domain.PolicyVO;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface PolicyMapper {
    void fetchPolicies(PolicyVO policyVO);
    int findByToken(Map<String, Object> policyToken);
    int getTotalCnt();
    int getTotalCntByKeyword(String keyword);
    List<PolicyVO> getPoliciesByPage(@Param("offset") int offset, @Param("limit") int limit);
    List<PolicyVO> getPoliciesByPageAndKeyword(@Param("offset") int offset,
                                               @Param("limit") int limit,
                                               @Param("keyword") String keyword);
    List<PolicyVO> recommendPolicies();
}
