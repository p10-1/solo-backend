package org.solo.mypage.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.solo.asset.domain.AssetVO;
import org.solo.member.domain.MemberVO;
import org.solo.mypage.domain.MypageVO;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Mapper
@Repository
public interface MypageMapper {
    void insertAssetData(AssetVO assetVO);

    boolean findAssetData(String userId);

    AssetVO getAssetData(String userId);

    void updateAssetData(AssetVO assetData);

    void updateMember(MemberVO memberVO);

    int getPoint(String userId);

    void updateCash(Map<String, Object> params);

}
