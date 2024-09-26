package org.solo.mypage.service;

import org.solo.asset.domain.AssetVO;
import org.solo.member.domain.MemberVO;

import java.util.Map;

public interface MypageService {
    void insertAssetData(AssetVO assetVO);
    boolean findAssetData(String userID);
    void updateAssetData(AssetVO assetData);
    void updateMember(MemberVO memberVO);

    AssetVO getAssetData(String userID);
    int getPoint(String kakaoId);
    boolean withdrawPoints(String kakaoId, int point);
    boolean updateCash(String userId, int cashAmount);

}
