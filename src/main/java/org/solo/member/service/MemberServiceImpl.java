package org.solo.member.service;

import org.solo.member.domain.MemberVO;
import org.solo.member.mapper.MemberMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MemberServiceImpl implements MemberService {
    private final MemberMapper memberMapper;

    @Autowired
    public MemberServiceImpl(MemberMapper memberMapper) {
        this.memberMapper = memberMapper;
    }

    public MemberVO findByKakaoId(String kakaoId) {
        return memberMapper.findByKakaoId(kakaoId);
    }

    public MemberVO insertNewUserInfo(String kakaoId, String nickName, String profileImage, String name, String email, String birthDate) {
        System.out.println("inserting new user info");
        MemberVO newUser = new MemberVO();
        newUser.setKakaoId(kakaoId);
        newUser.setNickName(nickName);
        newUser.setProfileImage(profileImage);
        newUser.setName(name);
        newUser.setEmail(email);
        newUser.setBirthDate(birthDate);

        memberMapper.insertNewUserInfo(newUser);
        return newUser;
    }

}











