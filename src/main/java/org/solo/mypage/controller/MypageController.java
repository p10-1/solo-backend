package org.solo.mypage.controller;

import org.solo.asset.domain.AssetVO;
import org.solo.mypage.domain.MypageVO;
import org.solo.mypage.service.MypageService;
import org.solo.mypage.service.MypageServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;



import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/mypage")
public class MypageController {

    private final MypageService mypageService;

    // MypageVO 객체 생성
    //MypageVO userData = new MypageVO();
    AssetVO userData = new AssetVO();


    @Autowired
    public MypageController(MypageService mypageService) {
        this.mypageService = mypageService;
    }

    @GetMapping({"", "/"})
    public String mypage() {
        return "mypage";
    }

    // create
    @PostMapping("/insert")
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
            userData.setUserID(userID);
        }

        userData.setCash(cash != null ? cash : 0);
        userData.setStock(stock != null ? stock : 0);
        userData.setProperty(property != null ? property : 0);
        userData.setDeposit(deposit != null ? deposit : 0);
        userData.setConsume(consume);

        // DB 저장
        // 존재여부 체크
        if (mypageService.findUserData(userID)) {
            session.setAttribute("message", "이미 저장된 데이터가 있습니다."); // 중복 메시지 설정
            return "redirect:/mypage"; // 마이페이지로 리다이렉트
        }

        // 서비스 메서드 호출하여 DB에 저장
        mypageService.insertUserData(userData);

        // DB에 저장된 데이터 콘솔에 출력
        System.out.println("DB에 저장된 데이터:");
        System.out.println("userID: " + userData.getUserID());
        System.out.println("Cash: " + userData.getCash());
        System.out.println("Stock: " + userData.getStock());
        System.out.println("Property: " + userData.getProperty());
        System.out.println("Deposit: " + userData.getDeposit());
        System.out.println("소비유형: " + userData.getConsume());

        session.setAttribute("message", "DB 저장 완료");
        // 저장 후 마이페이지로 리다이렉트
        return "redirect:/mypage";
    }

    //update
    @PostMapping("/update")
    public String updateUserData(HttpSession session,
                                 @RequestParam String consume,
                                 @RequestParam(required = false) Integer cash,
                                 @RequestParam(required = false) Integer stock,
                                 @RequestParam(required = false) Integer property,
                                 @RequestParam(required = false) Integer deposit){

        String userID = (String) session.getAttribute("kakaoId");
        System.out.println("update 수행중:" + userID);

        if (userID != null) {
            userData.setUserID(userID);
            userData.setConsume(consume);
            userData.setCash(cash != null ? cash : 0);
            userData.setStock(stock != null ? stock : 0);
            userData.setProperty(property != null ? property : 0);
            userData.setDeposit(deposit != null ? deposit : 0);

            mypageService.updateUserData(userData);

            session.setAttribute("message", "정보가 성공적으로 수정되었습니다.");
        } else {

            session.setAttribute("message", "사용자 정보를 찾을 수 없습니다.");
        }

        return "redirect:/mypage";

    }


}
