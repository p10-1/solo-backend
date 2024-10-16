package org.solo.member.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.solo.member.domain.MemberVO;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
@Mapper
public interface MemberMapper {

    // 카카오 ID 로 사용자를 찾는 메서드
    MemberVO findByKakaoId(@Param("userId") String userId);

    // 입력된 새로운 유저를 DB에 추가하는 메서드
    void insertNewUserInfo(MemberVO member);

    // 사용자의 포인트를 업데이트하는 메서드
    void updatePoint(Map<String, Object> params);

    // 서비스 내에서 사용할 닉네임의 중복을 테스트하는 메서드
    int checkUser(@Param("nickName") String nickName);

    // 출금요청 시 현재 포인트 조회
    int getPoint(String userId);
}
