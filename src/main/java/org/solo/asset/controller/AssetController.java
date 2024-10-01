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
            return ResponseEntity.ok(assets);
        }
        return ResponseEntity.notFound().build();
    }
    // 평균 자산 데이터를 가져오는 엔드포인트

    @GetMapping("/average")
    public ResponseEntity<Map<String, Double>> getAssetAverages() {
        Map<String, Double> averages = assetService.calculateAssetAverages();
        return ResponseEntity.ok(averages);
    }

}
