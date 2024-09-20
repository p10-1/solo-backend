package org.solo.mypage.service;

import org.solo.asset.domain.AssetVO;
import org.solo.member.domain.MemberVO;

public interface MypageService {
    void insertAssetData(AssetVO assetVO);
    boolean findAssetData(String userID);
    void updateAssetData(AssetVO userData);

    void updateMember(MemberVO memberVO);



    // asset
    //List<Asset> getAssets(String userID);
}
