package org.solo.mypage.controller;

import org.solo.asset.domain.AssetVO;
import org.solo.member.domain.MemberVO;
import org.solo.member.service.MemberService;
import org.solo.mypage.service.MypageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/api/mypage")
public class MypageController {

    private final MypageService mypageService;

    // VO객체생성
    AssetVO assetData = new AssetVO();



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

    public String saveUserData(HttpSession session,
                               @RequestParam(required = false) String consume,
                               @RequestParam(required = false) Integer cash,
                               @RequestParam(required = false) Integer stock,
                               @RequestParam(required = false) Integer property,
                               @RequestParam(required = false) Integer deposit) {

        // 세션에서 userID 가져오기
        String userID = (String) session.getAttribute("kakaoId");
        //확인용
        System.out.println("insert 수행중" + userID);

        // 세션에서 받아온 값 userID로 설정
        if (userID != null) {
            assetData.setUserID(userID);
        }

        assetData.setCash(cash != null ? cash : 0);
        assetData.setStock(stock != null ? stock : 0);
        assetData.setProperty(property != null ? property : 0);
        assetData.setDeposit(deposit != null ? deposit : 0);
        assetData.setConsume(consume);

        // DB 저장
        // 존재여부 체크
        if (mypageService.findAssetData(userID)) {
            session.setAttribute("message", "이미 저장된 데이터가 있습니다."); // 중복 메시지 설정
            return "redirect:/mypage"; // 마이페이지로 리다이렉트
        }

        mypageService.insertAssetData(assetData);

        // DB에 저장된 데이터 콘솔에 출력
        System.out.println("DB에 저장된 데이터:");
        System.out.println("userID: " + assetData.getUserID());
        System.out.println("Cash: " + assetData.getCash());
        System.out.println("Stock: " + assetData.getStock());
        System.out.println("Property: " + assetData.getProperty());
        System.out.println("Deposit: " + assetData.getDeposit());
        System.out.println("소비유형: " + assetData.getConsume());

        session.setAttribute("message", "DB 저장 완료");

        return "redirect:/mypage";
    }

    //update
    @PostMapping("/updateAsset")
    public String updateUserData(HttpSession session,
                                 @RequestParam String consume,
                                 @RequestParam(required = false) Integer cash,
                                 @RequestParam(required = false) Integer stock,
                                 @RequestParam(required = false) Integer property,
                                 @RequestParam(required = false) Integer deposit){

        String userID = (String) session.getAttribute("kakaoId");
        System.out.println("update 수행중:" + userID);

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

//    member수정
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
