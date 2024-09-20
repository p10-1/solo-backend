package org.solo.mypage.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.solo.asset.domain.AssetVO;
import org.solo.mypage.domain.MypageVO;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface MypageMapper {
    void insertUserData(AssetVO assetVO);

    boolean findUserData(String userID);

    void updateUserData(AssetVO userData);
}
