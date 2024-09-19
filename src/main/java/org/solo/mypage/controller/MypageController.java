package org.solo.mypage.controller;


import org.solo.mypage.domain.MypageVO;
import org.solo.mypage.service.MypageServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/mypage")
public class MypageController {

    private MypageServiceImpl mypageServiceImpl;

    @GetMapping({"","/"})
    public String mypage() {
        return "mypage";
    }

    @Autowired
    public MypageController(MypageServiceImpl mypageServiceImpl) {
        this.mypageServiceImpl = mypageServiceImpl;
    }

    @PostMapping("/saveUserData")
    public String saveUserData(@RequestParam String userID,
                               @RequestParam(required = false) Integer cash,
                               @RequestParam(required = false) Integer stock,
                               @RequestParam(required = false) Integer property,
                               @RequestParam(required = false) Integer deposit,
                               @RequestParam(required = false) Integer consume) {
        // UserDataVO 객체 생성
        MypageVO userData = new MypageVO();
        userData.setUserID(userID);
        userData.setCash(cash != null ? cash : 0); // null이면 0으로 설정
        userData.setStock(stock != null ? stock : 0);
        userData.setProperty(property != null ? property : 0);
        userData.setDeposit(deposit != null ? deposit : 0);
        userData.setConsume(consume != null ? consume : 0);

        // 서비스 메서드 호출하여 DB에 저장
        mypageServiceImpl.insertUserData(userData);


        // 저장 후 마이페이지로 리다이렉트
        return "redirect:/mypage";
    }
}
