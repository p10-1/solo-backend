package org.solo.member.service;

import org.solo.member.domain.MemberVO;

public interface MemberService {
    MemberVO findByKakaoId(String userId);
    MemberVO insertNewUserInfo(String userId, String nickName, String name, String email, String birthdate);
}



