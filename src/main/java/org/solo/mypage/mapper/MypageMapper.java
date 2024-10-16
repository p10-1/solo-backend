package org.solo.mypage.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.solo.asset.domain.AssetVO;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Mapper
@Repository
public interface MypageMapper {

    // 자산 데이터를 가져오는 메서드
    AssetVO getAssetData(String userId);

    // 사용자 유형 업데이트 메서드
    void updateUserType(Map<String, String> params);

    // 특정 사용자의 유형을 가져오는 메서드
    String getType(String userId);

    // 특정 사용자의 은행 계좌 목록을 가져오는 메서드
    AssetVO getBank(String userId);

    // 포인트 출금 메서드 추가
    boolean withdrawPoint(Map<String, Object> params);

    // 현금 업데이트 메서드 추가
    boolean updateCash(Map<String, Object> params);

}
