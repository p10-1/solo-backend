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

    public MemberVO findByKakaoId(String userId) {
        return memberMapper.findByKakaoId(userId);
    }

    public MemberVO insertNewUserInfo(String userId, String nickName, String userName, String email, String birthdate) {
        System.out.println("inserting new user info");
        MemberVO newUser = new MemberVO();
        newUser.setUserId(userId);
        newUser.setNickName(nickName);
        newUser.setUserName(userName);
        newUser.setEmail(email);
        newUser.setBirthdate(birthdate);
        memberMapper.insertNewUserInfo(newUser);
        return newUser;
    }

    public boolean checkUser(String nickName) {
        return memberMapper.checkUser(nickName) > 0;
    }
}











