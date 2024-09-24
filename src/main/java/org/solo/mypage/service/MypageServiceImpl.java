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
    public void insertAssetData(AssetVO assetData) {
        mypageMapper.insertAssetData(assetData);
    }


    @Override
    public boolean findAssetData(String userID) {
        return mypageMapper.findAssetData(userID);
    }

    @Override
    public void updateAssetData(AssetVO assetData) {
        mypageMapper.updateAssetData(assetData);
    }

    @Override
    public void updateMember(MemberVO memberVO) {
        mypageMapper.updateMember(memberVO);
    }

    @Override
    public MemberVO getPoint(String kakaoId){
        return mypageMapper.getPoint(kakaoId);
    }
}