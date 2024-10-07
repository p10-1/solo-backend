package org.solo.asset.controller;

import org.solo.asset.domain.AssetVO;
import org.solo.asset.service.AssetService;
import org.solo.mypage.service.MypageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/asset")
public class AssetController {
    private final AssetService assetService;

    @Autowired
    public AssetController(AssetService assetService) {
        this.assetService = assetService;
    }
    // 자산 데이터를 가져오는 엔드포인트

    @GetMapping("")
    public ResponseEntity<List<AssetVO>> getAsset(HttpSession session, HttpServletRequest request) {
        System.out.println("controller실행");
        // 세션이 유효한지 확인

        if (session == null || !request.isRequestedSessionIdValid()) {
            System.out.println("세션이 무효화 상태입니다.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        String userId = (String) session.getAttribute("userId");
        System.out.println("get 수행중:" + userId);
        // 사용자 ID가 있으면 자산 데이터를 서비스에서 가져옴

        if (userId != null) {
            List<AssetVO> assets = assetService.getAssetData(userId,7); //우선 6개월만
            System.out.println("AssetController: Retrieved assets: " + assets);

            return ResponseEntity.ok(assets);
        }
        return ResponseEntity.notFound().build();
    }
    // 평균 자산 데이터를 가져오는 엔드포인트

    @GetMapping("/average")
    public ResponseEntity<Map<String, Double>> getAssetAverages() {
        System.out.println("AssetController: getAssetAverages method called");

        Map<String, Double> averages = assetService.calculateAssetAverages();
        System.out.println("AssetController: Calculated averages: " + averages);

        return ResponseEntity.ok(averages);
    }

}
//INSERT INTO `userAsset` (
//        `userId`,
//        `cashBank`,
//        `cashAccount`,
//        `cash`,
//        `stockBank`,
//        `stockAccount`,
//        `stock`,
//        `depositBank`,
//        `depositAccount`,
//        `deposit`,
//        `insuranceCompany`,
//        `insuranceName`,
//        `insurance`,
//        `type`,
//        `loanAmount`,
//        `loanPurpose`,
//        `period`,
//        `interest`
//        ) VALUES (
//    '3000220000',
//            '["우리은행", "신한은행", "국민은행"]',
//            '["123-456-7890", "987-654-3210", "456-789-0123"]',
//            '["100000", "150000", "75000"]',
//            '["미래에셋", "삼성증권", "키움증권"]',
//            '["111-222-3333", "444-555-6666", "777-888-9999"]',
//            '["300000", "250000", "180000"]',
//            '["농협은행", "하나은행", "카카오뱅크"]',
//            '["333-444-5555", "666-777-8888", "999-000-1111"]',
//            '["500000", "300000", "200000"]',
//            '["삼성생명", "한화생명"]',
//            '["종신보험", "암보험"]',
//            '["1000000", "500000"]',
//            '적극투자형',
//            2500000,
//            '주택구입',
//            36,
//            3.75
//);