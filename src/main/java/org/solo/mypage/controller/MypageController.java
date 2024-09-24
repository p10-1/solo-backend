package org.solo.mypage.controller;

import org.solo.asset.domain.AssetVO;
import org.solo.member.domain.MemberVO;
import org.solo.member.service.MemberService;
import org.solo.mypage.service.MypageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/api/mypage")
public class MypageController {

    private final MypageService mypageService;


    @Autowired
    public MypageController(MypageService mypageService) {
        this.mypageService = mypageService;
    }

    @GetMapping({"", "/"})
    public String mypage() {
        return "mypage";
    }

    // create
    @PostMapping("/insertAsset")
    public ResponseEntity<String> saveUserData(HttpSession session, @RequestBody Map<String, Object> data) {
        String userID = (String) session.getAttribute("kakaoId");
        System.out.println("insert 수행중: " + userID);
        System.out.println(data);

        AssetVO assetData = new AssetVO();

        if (userID != null) {
            assetData.setUserID(userID);
        }

        // 데이터 매핑
        if (data.get("assets") instanceof Map) {
            Map<String, Object> assets = (Map<String, Object>) data.get("assets");

            // 현금 자산
            if (assets.get("현금자산") instanceof List) {
                List<Map<String, Object>> cashAssets = (List<Map<String, Object>>) assets.get("현금자산");
                if (!cashAssets.isEmpty()) {
                    assetData.setCash((int) cashAssets.get(0).get("amount"));
                }
            }

            // 증권 자산
            if (assets.get("증권자산") instanceof List) {
                List<Map<String, Object>> stockAssets = (List<Map<String, Object>>) assets.get("증권자산");
                if (!stockAssets.isEmpty()) {
                    assetData.setStock((int) stockAssets.get(0).get("amount"));
                }
            }

            // 부동산 자산
            if (assets.get("부동산자산") instanceof List) {
                List<Map<String, Object>> propertyAssets = (List<Map<String, Object>>) assets.get("부동산자산");
                if (!propertyAssets.isEmpty()) {
                    assetData.setProperty((int) propertyAssets.get(0).get("amount"));
                }
            }

            // 예적금 자산
            if (assets.get("예적금자산") instanceof List) {
                List<Map<String, Object>> depositAssets = (List<Map<String, Object>>) assets.get("예적금자산");
                if (!depositAssets.isEmpty()) {
                    assetData.setDeposit((int) depositAssets.get(0).get("amount"));
                }
            }
        }

        // 소비 유형 설정
        assetData.setConsume((String) data.get("consumeType"));

        // 대출
        if (data.get("loan") instanceof Map) {
            Map<String, Object> loanData = (Map<String, Object>) data.get("loan");
            if (loanData != null) {
                Integer loanAmount = (Integer) loanData.get("amount");
                String loanPurpose = (String) loanData.get("purpose");
                Integer period = (Integer) loanData.get("period");

                // 대출 정보를 AssetVO에 설정
                assetData.setLoanAmount(loanAmount);
                assetData.setLoanPurpose(loanPurpose);
                assetData.setPeriod(period);
            }
        }

        // DB 저장
        if (mypageService.findAssetData(userID)) {
            session.setAttribute("message", "이미 저장된 데이터가 있습니다.");
            return ResponseEntity.status(409).body("이미 저장된 데이터가 있습니다.");
        }
        mypageService.insertAssetData(assetData);

        // DB에 저장된 데이터 콘솔에 출력 (확인용)
        System.out.println("DB에 저장된 데이터:");
        System.out.println("userID: " + assetData.getUserID());
        System.out.println("Cash: " + assetData.getCash());
        System.out.println("Stock: " + assetData.getStock());
        System.out.println("Property: " + assetData.getProperty());
        System.out.println("Deposit: " + assetData.getDeposit());
        System.out.println("소비유형: " + assetData.getConsume());
        System.out.println("대출액: " + assetData.getLoanAmount());
        System.out.println("대출 목적: " + assetData.getLoanPurpose());
        System.out.println("대출 기간: " + assetData.getPeriod());

        session.setAttribute("message", "DB 저장 완료");

        return ResponseEntity.ok("저장 완료");
    }



    // update
    @PostMapping("/updateAsset")
    public String updateUserData(HttpSession session,
                                 @RequestParam String consume,
                                 @RequestParam(required = false) Integer cash,
                                 @RequestParam(required = false) Integer stock,
                                 @RequestParam(required = false) Integer property,
                                 @RequestParam(required = false) Integer deposit){

        String userID = (String) session.getAttribute("kakaoId");
        System.out.println("update 수행중:" + userID);

        // AssetVO 객체 생성
        AssetVO assetData = new AssetVO();
        if (userID != null) {
            assetData.setUserID(userID);
            assetData.setConsume(consume);
            assetData.setCash(cash != null ? cash : 0);
            assetData.setStock(stock != null ? stock : 0);
            assetData.setProperty(property != null ? property : 0);
            assetData.setDeposit(deposit != null ? deposit : 0);

            mypageService.updateAssetData(assetData);
            session.setAttribute("message", "정보가 성공적으로 수정되었습니다.");
        } else {
            session.setAttribute("message", "사용자 정보를 찾을 수 없습니다.");
        }

        return "redirect:/mypage";
    }

    // member 수정
    @PostMapping("/updateMember")
    public String updateUser(MemberVO memberVO, Model model, HttpSession session){

        String kakaoId = (String) session.getAttribute("kakaoId");
        System.out.println("user update 수행중:" + kakaoId);

        if (kakaoId != null) {
            memberVO.setKakaoId(kakaoId);
            mypageService.updateMember(memberVO);
            session.setAttribute("message", "정보가 성공적으로 수정되었습니다.");
        } else {
            session.setAttribute("message", "사용자 정보를 찾을 수 없습니다.");
        }

        model.addAttribute("userData", memberVO);
        return "redirect:/mypage";
    }
}

