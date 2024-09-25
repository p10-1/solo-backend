package org.solo.mypage.service;

import org.solo.asset.domain.AssetVO;
import org.solo.member.domain.MemberVO;
import org.solo.member.mapper.MemberMapper;
import org.solo.mypage.domain.MypageVO;
import org.solo.mypage.mapper.MypageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
public class MypageServiceImpl implements MypageService {
    private final MypageMapper mypageMapper;

    private final MemberMapper memberMapper;

    @Autowired
    public MypageServiceImpl(MypageMapper mypageMapper, MemberMapper memberMapper) {
        this.mypageMapper = mypageMapper;
        this.memberMapper = memberMapper;
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
    public int getPoint(String kakaoId){
        return mypageMapper.getPoint(kakaoId);
    }

    @Override
    public boolean withdrawPoints(String kakaoId, int point) {
        MemberVO member = memberMapper.findByKakaoId(kakaoId);

        System.out.println("withdrawPoints 서비스 실행");
        if (member != null && member.getPoint() >= point) {
            int newPoint = member.getPoint() - point;

            Map<String, Object> params = new HashMap<>();
            params.put("kakaoId", kakaoId);
            params.put("newPoint", newPoint);

            memberMapper.updatePoint(params);
            return true;
        }

        return false; // 출금 실패 (회원 정보가 없거나 포인트가 부족한 경우)
    }

    @Override
    public boolean updateCash(String userId, int cashAmount) {
        AssetVO asset = mypageMapper.getAssetData(userId);
        System.out.println("updateCash service 들어옴"+asset);

        if (asset != null) {
            // 기존 현금에 추가 금액을 더함
            int currentCash = asset.getCash();
            int updatedCash = currentCash + cashAmount; // 새로운 cash 값 계산

            System.out.println("update: "+updatedCash+"cur: "+currentCash+"new: "+cashAmount);

            Map<String, Object> params = new HashMap<>();
            params.put("userId", userId);
            params.put("updatedCash", updatedCash);

            mypageMapper.updateCash(params);
            return true;
        }

        return false;
    }

}