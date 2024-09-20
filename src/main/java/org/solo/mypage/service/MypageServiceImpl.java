package org.solo.mypage.service;

import org.solo.mypage.domain.MypageVO;
import org.solo.mypage.mapper.MypageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MypageServiceImpl implements MypageService {
    private final MypageMapper mypageMapper;

    @Autowired
    public MypageServiceImpl(MypageMapper mypageMapper) {
        this.mypageMapper = mypageMapper;
    }

    @Override
    public void insertUserData(MypageVO userData) {
        mypageMapper.insertUserData(userData);
    }

    @Override
    public boolean findUserData(String userID){
        return mypageMapper.findUserData(userID);
    }

    @Override
    public void updateUserData(MypageVO userData) {
        mypageMapper.updateUserData(userData);
    }



}
