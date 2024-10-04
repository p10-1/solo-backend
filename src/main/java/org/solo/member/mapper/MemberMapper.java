package org.solo.member.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.solo.member.domain.MemberVO;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
@Mapper
public interface MemberMapper {
    MemberVO findByKakaoId(@Param("userId") String userId);
    void insertNewUserInfo(MemberVO member);
    void updatePoint(Map<String, Object> params);// 포인트 차감시 UPDATE
    int checkUser(@Param("nickName") String nickName);

    //출금요청 시 현재 포인트 조회
    int getPoint(String userId);
}
