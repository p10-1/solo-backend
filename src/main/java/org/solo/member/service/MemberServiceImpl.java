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

    @Override
    public MemberVO findByKakaoId(String userId) {
        return memberMapper.findByKakaoId(userId);
    }

    @Override
    public MemberVO insertNewUserInfo(String userId, String nickName, String userName, String email, String birthdate) {
        MemberVO newUser = new MemberVO(userId, nickName, userName, email, birthdate, 0);
        memberMapper.insertNewUserInfo(newUser);
        return newUser;
    }

    @Override
    public boolean checkUser(String nickName) {
        return memberMapper.checkUser(nickName) > 0;
    }
}











