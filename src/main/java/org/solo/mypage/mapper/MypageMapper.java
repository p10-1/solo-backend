package org.solo.mypage.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.solo.asset.domain.AssetVO;
import org.solo.member.domain.MemberVO;
import org.solo.mypage.domain.MypageVO;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface MypageMapper {
    void insertAssetData(AssetVO assetVO);

    boolean findAssetData(String userID);

    void updateAssetData(AssetVO assetData);

    void updateMember(MemberVO memberVO);
}
