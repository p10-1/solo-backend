package org.solo.member.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.solo.member.domain.MemberVO;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
@Mapper
public interface MemberMapper {
    MemberVO findByKakaoId(@Param("kakaoId") String kakaoId);
    void insertNewUserInfo(MemberVO member);
    void updatePoint(Map<String, Object> params);
}
