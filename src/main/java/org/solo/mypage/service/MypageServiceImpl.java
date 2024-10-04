package org.solo.mypage.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.solo.asset.domain.AssetVO;
import org.solo.member.domain.MemberVO;
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

    @Autowired
    public MypageServiceImpl(MypageMapper mypageMapper, MemberMapper memberMapper) {
        this.mypageMapper = mypageMapper;
        this.memberMapper = memberMapper;
    }

    // 자산을 가져오는 서비스
    @Override
    public AssetVO getAssetData(String userId) {
        return mypageMapper.getAssetData(userId);
    }

    // 자산을 저장하는 서비스
    @Override
    public void updateAsset(AssetVO assetVO) {
        mypageMapper.updateAsset(assetVO);
    }

    @Override
    public AssetVO checkAssetData(String userId) {
        return mypageMapper.checkAssetData(userId);
    }

    @Override
    public void updateUserType(String userId, String type) {
        Map<String, String> params = new HashMap<>();
        params.put("userId", userId);
        params.put("type", type);
        mypageMapper.updateUserType(params);
    }

    @Override
    public String getType(String userId) {
        return mypageMapper.getType(userId);
    }

    @Override
    public List<String> getBank(String userId) {
        return mypageMapper.getBank(userId);
    }

    @Override
    public ResponseEntity<?> withdrawPoints(String userId, Integer idx, Integer withdrawAmount) {
        // 유저의 포인트 가져오기
        Integer currentPoints = getPoint(userId);

        // 유효성 검사
        if (currentPoints != null && currentPoints >= withdrawAmount) {
            // 출금 처리
            boolean withdrawSuccess = withdrawPoint(userId, withdrawAmount);
            System.out.println("포인트출금"+withdrawSuccess);
            if (withdrawSuccess) {
                // 현금 업데이트
                boolean updateCashSuccess = updateCash(userId, withdrawAmount, idx);
                System.out.println(updateCashSuccess);
                if (updateCashSuccess) {
                    return ResponseEntity.ok("출금 및 현금 추가가 성공적으로 완료되었습니다.");
                } else {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("현금 추가 실패");
                }
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("출금 실패");
            }
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("출금할 포인트가 부족하거나 유효하지 않음");
        }
    }

    public int getPoint(String userId) {
        return memberMapper.getPoint(userId);
    }

    private boolean withdrawPoint(String userId, Integer withdrawAmount) {
        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        params.put("withdrawAmount", withdrawAmount);  // withdrawAmount 추가

        return mypageMapper.withdrawPoint(params); // MyBatis Mapper 호출
    }

    private boolean updateCash(String userId, Integer withdrawAmount, Integer idx) {
        // 현재 사용자의 cash 값을 가져옵니다.

        System.out.println("updatecash들어옴"+userId);
        AssetVO asset = mypageMapper.checkAssetData(userId);
        System.out.println(asset);
        if (asset == null || asset.getCash() == null) {
            return false; // 자산 데이터가 없거나 cash가 없는 경우
        }


        // cash 값을 JSON 배열로 변환
        String cashJson = asset.getCash(); // 예: ["50000", "20000"]
        ObjectMapper objectMapper = new ObjectMapper();
        List<Integer> cashList;

        try {
            // JSON 문자열을 List로 변환
            cashList = objectMapper.readValue(cashJson, new TypeReference<List<Integer>>() {});
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return false; // JSON 파싱 오류
        }

        // accountIndex가 유효한지 확인
        if (idx < 0 || idx >= cashList.size()) {
            return false; // 인덱스 범위 초과
        }

        // 해당 인덱스의 현금에 withdrawAmount를 더함
        int currentCash = cashList.get(idx);
        cashList.set(idx, currentCash + withdrawAmount); // 현금 업데이트

        // 변경된 리스트를 다시 JSON 문자열로 변환
        String updatedCashJson;
        try {
            updatedCashJson = objectMapper.writeValueAsString(cashList);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return false; // JSON 변환 오류
        }

        // DB에 업데이트
        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        params.put("updatedCash", updatedCashJson);

        return mypageMapper.updateCash(params);
    }
}
