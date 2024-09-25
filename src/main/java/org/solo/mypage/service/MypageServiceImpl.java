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
        if (member != null && member.getPoint() >= point) {
            // 포인트 차감
            int newPoint = member.getPoint() - point;

            Map<String, Object> params = new HashMap<>();
            params.put("kakaoId", kakaoId);
            params.put("newPoint", newPoint);

            memberMapper.updatePoint(params); // 업데이트
            return true; // 성공적으로 출금됨
        }
        return false; // 출금 실패
    }

    @Override
    public boolean updateCash(String userId, double cashAmount) {
        // 사용자 자산 정보를 가져옴
        AssetVO asset = mypageMapper.getAssetData(userId);
        if (asset != null) {
            double newCash = asset.getCash() + cashAmount; // 기존 현금에 추가 금액을 더함

            // 매개변수를 담을 Map 생성
            Map<String, Object> params = new HashMap<>();
            params.put("userId", userId);
            params.put("newCash", newCash);

            // 현금 업데이트 호출
            mypageMapper.updateCash(params);
            return true; // 성공적으로 업데이트됨
        }
        return false; // 자산 데이터가 없거나 업데이트 실패
    }
}