package org.solo.mypage.service;

import org.solo.asset.domain.AssetVO;
import org.solo.member.domain.MemberVO;
import org.solo.member.mapper.MemberMapper;
import org.solo.mypage.mapper.MypageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
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

    // 자산을 가져오는 서비스
    @Override
    public AssetVO getAssetData(String userId){
        System.out.println("서비스까지 불러옴");
        return mypageMapper.getAssetData(userId);
    }

    // 자산을 저장하는 서비스
    @Override
    public void updateAsset(AssetVO assetVO) {
        System.out.println("updateAsset 서비스호출");
        // 자산 정보를 업데이트
        mypageMapper.updateAsset(assetVO);
    }


    @Override
    public AssetVO checkAssetData(String userId) {
        return mypageMapper.checkAssetData(userId);
    }
    @Override
    public int getPoint(String userId){
        return mypageMapper.getPoint(userId);
    }


    @Override
    public void updateUserType(String userId, String type) {
        System.out.println("update 서비스 들어옴");
        Map<String, String> params = new HashMap<>();
        params.put("userId", userId);
        params.put("type", type);
        mypageMapper.updateUserType(params);
    }

    @Override
    public String getType(String userId) {
        return mypageMapper.getType(userId);
    }

    @Override
    public List<String> getBank(String userId) {
        return mypageMapper.getBank(userId);
    }


//    @Override
//    public void insertAssetData(AssetVO assetData) {
//        mypageMapper.insertAssetData(assetData);
//    }
//
//    @Override
//    public boolean findAssetData(String userId) {
//        return mypageMapper.findAssetData(userId);
//    }
//
//    @Override
//    public void updateAssetData(AssetVO assetData) {
//        mypageMapper.updateAssetData(assetData);
//    }
//
//    @Override
//    public void updateMember(MemberVO memberVO) {
//        mypageMapper.updateMember(memberVO);
//    }



//     // 자산의 cash update
//    @Override
//    public boolean updateCash(String userId, int cashAmount) {
//        AssetVO asset = mypageMapper.checkAssetData(userId);
//        System.out.println("updateCash service 들어옴"+asset);
//
//        if (asset != null) {
//            // 기존 현금에 추가 금액을 더함
//            int currentCash = asset.getCash();
//            int updatedCash = currentCash + cashAmount; // 새로운 cash 값 계산
//
//            System.out.println("update: "+updatedCash+"cur: "+currentCash+"new: "+cashAmount);
//
//            Map<String, Object> params = new HashMap<>();
//            params.put("userId", userId);
//            params.put("updatedCash", updatedCash);
//
//            mypageMapper.updateCash(params);
//            return true;
//        }
//
//        return false;
//    }



    @Override
    public boolean withdrawPoints(String userId, int point) {
        MemberVO member = memberMapper.findByKakaoId(userId);

        System.out.println("withdrawPoints 서비스 실행");
        if (member != null && member.getPoint() >= point) {
            int newPoint = member.getPoint() - point;

            Map<String, Object> params = new HashMap<>();
            params.put("userId", userId);
            params.put("newPoint", newPoint);

            memberMapper.updatePoint(params);
            return true;
        }

        return false; // 출금 실패 (회원 정보가 없거나 포인트가 부족한 경우)
    }



}