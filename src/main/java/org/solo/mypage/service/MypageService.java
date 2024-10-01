package org.solo.mypage.service;

import org.solo.asset.domain.AssetVO;
import org.solo.member.domain.MemberVO;

import java.util.List;

public interface MypageService {

    AssetVO getAssetData(String userId);
    void updateAsset(AssetVO assetVO);
    AssetVO checkAssetData(String userId);
    int getPoint(String userId);
    void updateUserType(String userId, String type);
    String getType(String userId);
    List<String> getBank(String userId);


//    void insertAssetData(AssetVO assetVO);
//    boolean findAssetData(String userId);
//    void updateAssetData(AssetVO assetData);
//    void updateMember(MemberVO memberVO);

//    boolean withdrawPoints(String userId, int point);
//    boolean updateCash(String userId, int cashAmount);


}
