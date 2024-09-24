package org.solo.mypage.service;

import org.solo.asset.domain.AssetVO;
import org.solo.member.domain.MemberVO;

public interface MypageService {
    void insertAssetData(AssetVO assetVO);
    boolean findAssetData(String userID);
    void updateAssetData(AssetVO assetData);

    void updateMember(MemberVO memberVO);

    MemberVO getPoint(String kakaoId);



    // asset
    //List<Asset> getAssets(String userID);
}
