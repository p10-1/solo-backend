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

@RestController
@RequestMapping("/api/asset")
public class AssetController {
    private final AssetService assetService;

    @Autowired
    public AssetController(AssetService assetService) {
        this.assetService = assetService;
    }

    @GetMapping("")
    public ResponseEntity<List<AssetVO>> getAsset(HttpSession session, HttpServletRequest request) {
        System.out.println("controller실행");
        if (session == null || !request.isRequestedSessionIdValid()) {
            System.out.println("세션이 무효화 상태입니다.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        String userId = (String) session.getAttribute("userId");
        System.out.println("get 수행중:" + userId);
        if (userId != null) {
            List<AssetVO> assets = assetService.getAssetData(userId,6); //우선 6개월만
            return ResponseEntity.ok(assets);
        }
        return ResponseEntity.notFound().build();
    }
}
