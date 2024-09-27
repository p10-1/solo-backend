package org.solo.asset.controller;

import org.solo.asset.domain.AssetVO;
import org.solo.asset.service.AssetService;
import org.solo.mypage.domain.MypageVO;
import org.solo.mypage.service.MypageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("api/asset")
public class AssetController {
    private final AssetService assetService;

    @Autowired
    public AssetController(AssetService assetService) {
        this.assetService = assetService;
    }

    @GetMapping("")
    public ResponseEntity<AssetVO> getAsset(HttpSession session) {
        String userId = (String) session.getAttribute("userId");
        System.out.println("get 수행중:" + userId);
        if (userId != null) {
            AssetVO asset = assetService.getAssetData(userId);
            return ResponseEntity.ok(asset);
        }
        return ResponseEntity.notFound().build();

    }
}




