package org.solo.mypage.service;

import org.solo.asset.domain.AssetVO;
import org.solo.member.domain.MemberVO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface MypageService {

    // 자산 데이터를 가져오는 메서드
    AssetVO getAssetData(String userId);

    // 자산 데이터를 업데이트하는 메서드
    void updateAsset(AssetVO assetVO);

    // 자산 데이터가 존재하는지 확인하는 메서드
    AssetVO checkAssetData(String userId);

    // 특정 사용자의 포인트를 가져오는 메서드
    int getPoint(String userId);

    // 사용자 유형을 업데이트하는 메서드
    void updateUserType(String userId, String type);

    // 특정 사용자의 유형을 가져오는 메서드
    String getType(String userId);

    // 특정 사용자의 은행 계좌 목록을 가져오는 메서드
    List<String> getBank(String userId);

    // 사용자의 포인트를 출금하는 메서드
    ResponseEntity<?> withdrawPoints(String userId, Integer idx, Integer withdrawAmount);
}
