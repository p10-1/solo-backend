package org.solo.mypage.controller;

import org.solo.mypage.domain.MypageVO;
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

    private MypageServiceImpl mypageServiceImpl;

    @Autowired
    public MypageController(MypageServiceImpl mypageServiceImpl) {
        this.mypageServiceImpl = mypageServiceImpl;
    }

    @GetMapping({"", "/"})
    public String mypage() {
        return "mypage";
    }

    // 저장 , 수정만
    @PostMapping("/saveUserData")
    public String saveUserData(HttpSession session,
                               @RequestParam(required = false) Integer cash,
                               @RequestParam(required = false) Integer stock,
                               @RequestParam(required = false) Integer property,
                               @RequestParam(required = false) Integer deposit,
                               @RequestParam(required = false) Integer consume) {

        // 세션에서 userID 가져오기
        String userID = (String) session.getAttribute("kakaoId"); // 세션에서 userID를 직접 가져옵니다.

        System.out.println("세션에서 가져온 값" + userID);
        // MypageVO 객체 생성
        MypageVO userData = new MypageVO();

        // userID 설정
        if (userID != null) {
            userData.setUserID(userID); // userID 설정
        }

        userData.setCash(cash != null ? cash : 0);
        userData.setStock(stock != null ? stock : 0);
        userData.setProperty(property != null ? property : 0);
        userData.setDeposit(deposit != null ? deposit : 0);
        userData.setConsume(consume != null ? consume : 0);

        // 서비스 메서드 호출하여 DB에 저장
        mypageServiceImpl.insertUserData(userData);

        // DB에 저장된 데이터 콘솔에 출력
        System.out.println("DB에 저장된 데이터:");
        System.out.println("userID: " + userData.getUserID());
        System.out.println("Cash: " + userData.getCash());
        System.out.println("Stock: " + userData.getStock());
        System.out.println("Property: " + userData.getProperty());
        System.out.println("Deposit: " + userData.getDeposit());
        System.out.println("Consume: " + userData.getConsume());

        session.setAttribute("message", "DB 저장 완료");
        // 저장 후 마이페이지로 리다이렉트
        return "redirect:/mypage";
    }
}
