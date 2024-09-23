package org.solo.mypage.service;

import org.solo.asset.domain.AssetVO;
import org.solo.member.domain.MemberVO;
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
    public void insertAssetData(AssetVO userData) {
        mypageMapper.insertAssetData(userData);
    }

    @Override
    public boolean findAssetData(String userID){
        return mypageMapper.findAssetData(userID);
    }

    @Override
    public void updateAssetData(AssetVO userData) {
        mypageMapper.updateAssetData(userData);
    }

    @Override
    public void updateMember(MemberVO memberVO){
        mypageMapper.updateMember(memberVO);
    }

}
