package org.solo.mypage.service;

import org.solo.asset.domain.AssetVO;
import org.solo.member.domain.MemberVO;

public interface MypageService {

    AssetVO getAssetData(String userId);
    void updateAsset(AssetVO assetVO);
//    void insertAssetData(AssetVO assetVO);
//    boolean findAssetData(String userId);
//    void updateAssetData(AssetVO assetData);
//    void updateMember(MemberVO memberVO);

    AssetVO checkAssetData(String userId);
    int getPoint(String userId);
    boolean withdrawPoints(String userId, int point);
//    boolean updateCash(String userId, int cashAmount);


}
