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

    // DB에 정책을 저장하는 메서드
    void fetchPolicies(PolicyVO policyVO);

    // 정책들의 중복 테스트하는 메서드
    int findByToken(Map<String, Object> policyToken);

    // 정책의 개수 불러오는 메서드
    int getTotalCnt(@Param("category") String category);

    // 키워드 검색 후 정책 개수 불러오는 메서드
    int getTotalCntByKeyword(@Param("keyword") String keyword, @Param("category") String category);

    // 정책 리스트 불러오는 메서드
    List<PolicyVO> getPoliciesByPage(@Param("offset") int offset, @Param("limit") int limit, @Param("category") String category);

    // 키워드 별 정책 리스트 불러오는 메서드
    List<PolicyVO> getPoliciesByPageAndKeyword(@Param("offset") int offset, @Param("limit") int limit, @Param("keyword") String keyword, @Param("category") String category);

    // 추천 정책 리스트 불러오는 메서드
    List<PolicyVO> recommendPolicies();
}
