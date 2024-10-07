package org.solo.mypage.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.solo.asset.domain.AssetVO;
import org.solo.member.mapper.MemberMapper;
import org.solo.mypage.mapper.MypageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class MypageServiceImpl implements MypageService {
    private final MypageMapper mypageMapper;
    private final MemberMapper memberMapper;
    private final ObjectMapper objectMapper;

    // 오류 메세지 정의
    private static final String WITHDRAW_FAILURE_MESSAGE = "출금 실패";
    private static final String INSUFFICIENT_POINTS_MESSAGE = "출금할 포인트가 부족하거나 유효하지 않음";
    private static final String CASH_UPDATE_FAILURE_MESSAGE = "현금 추가 실패";

    @Autowired
    public MypageServiceImpl(MypageMapper mypageMapper, MemberMapper memberMapper, ObjectMapper objectMapper) {
        this.mypageMapper = mypageMapper;
        this.memberMapper = memberMapper;
        this.objectMapper = objectMapper;
    }

    // 사용자 자산 불러오기
    @Override
    public AssetVO getAssetData(String userId) {
        return mypageMapper.getAssetData(userId);
    }

    // 사용자 자산 업데이트
    @Override
    public void updateAsset(AssetVO assetVO) {
        mypageMapper.updateAsset(assetVO);
    }

    // 사용자 유형 불러오기
    @Override
    public String getType(String userId) {
        return mypageMapper.getType(userId);
    }

    // 사용자 유형 업데이트
    @Override
    public void updateUserType(String userId, String type) {
        System.out.println("updateuserType service");
        Map<String, String> params = new HashMap<>();
        params.put("userId", userId);
        params.put("type", type);
        mypageMapper.updateUserType(params);
    }

    // 사용자 계좌 불러오기 (포인트 출금용)
    @Override
    public List<String> getBank(String userId) {
        return mypageMapper.getBank(userId);
    }

    // 포인트 받아오기
    public int getPoint(String userId) {
        return memberMapper.getPoint(userId);
    }

    // 포인트 출금 전체 메소드
    @Override
    public ResponseEntity<?> withdrawPoints(String userId, Integer idx, Integer withdrawAmount) {
        // 포인트 출금
        if (withdrawPoint(userId, withdrawAmount)) {
            updateCash(userId, withdrawAmount, idx); // 현금 업데이트
            return ResponseEntity.ok("success"); // 성공 시 "success" 반환
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(WITHDRAW_FAILURE_MESSAGE);
        }
    }

    // 포인트 출금 처리
    private boolean withdrawPoint(String userId, Integer withdrawAmount) {
        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        params.put("withdrawAmount", withdrawAmount);
        return mypageMapper.withdrawPoint(params);
    }

    // 현금자산 업데이트 처리
    private void updateCash(String userId, Integer withdrawAmount, Integer idx) {
        // DB에서 자산 데이터 가져와서 해당 계좌의 금액만 currentCash에 할당
        AssetVO asset = mypageMapper.getAssetData(userId);
        List<Integer> cashList = (List<Integer>) convertCashData(asset.getCash()); // JSON 문자열을 List로 변환
        int currentCash = cashList.get(idx);
        cashList.set(idx, currentCash + withdrawAmount);

        // 업데이트된 현금 리스트를 DB에 저장
        saveUpdatedCash(userId, cashList);
    }

    // JSON <-> List
    private Object convertCashData(Object cashData) {
        try {
            if (cashData instanceof String) {
                // JSON 문자열을 List로 변환
                return objectMapper.readValue((String) cashData, new TypeReference<List<Integer>>() {});
            } else if (cashData instanceof List) {
                // List를 JSON 문자열로 변환
                return objectMapper.writeValueAsString(cashData);
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null; // 변환 오류 또는 잘못된 타입
    }

    // 업데이트 된 현금자산 DB에 저장
    private void saveUpdatedCash(String userId, List<Integer> cashList) {
        String updatedCashJson = (String) convertCashData(cashList); // List를 JSON으로 변환
        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        params.put("updatedCash", updatedCashJson);
        mypageMapper.updateCash(params);
    }
}
