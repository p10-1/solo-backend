package org.solo.mypage.service;

import org.solo.asset.domain.AssetVO;

public interface MypageService {
    void insertUserData(AssetVO assetVO);
    boolean findUserData(String userID);
    void updateUserData(AssetVO userData);

    // asset
    //List<Asset> getAssets(String userID);
}
