package org.solo.member.service;

import org.solo.member.domain.MemberVO;

public interface MemberService {
    MemberVO findByKakaoId(String kakaoId);
    MemberVO insertNewUserInfo(String kakaoId, String nickName, String name, String email, String birthDate);
}



