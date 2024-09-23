package org.solo.asset.controller;

import org.solo.asset.service.AssetService;
import org.solo.mypage.service.MypageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/asset")
public class AssetController {

    @Autowired
    private AssetService assetService;
    private MypageService mypageService;
    @GetMapping({"","/"})
    public String asset(HttpSession session, Model model) {

//        String userID = (String) session.getAttribute("kakaoId");
//
//        if(userID != null) {
//            List<Asset> assets = mypageService.getAssets(userID);
//            model.addAttribute("assets",assets);
//        } else {
//            model.addAllAttributes("assets",null);
//        }
        return "asset";
    }


}
