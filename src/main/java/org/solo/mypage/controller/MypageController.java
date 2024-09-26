package org.solo.mypage.controller;

import org.solo.asset.domain.AssetVO;
import org.solo.member.domain.MemberVO;
import org.solo.member.service.MemberService;
import org.solo.mypage.service.MypageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

    @PostMapping("/insertAsset")
    public ResponseEntity<String> saveUserData(HttpSession session, @RequestBody Map<String, Object> data) {
        String userID = (String) session.getAttribute("kakaoId");
        System.out.println("insert 수행중: " + userID);

        AssetVO assetData = new AssetVO();

        if (userID != null) {
            assetData.setUserID(userID);
        }
        // 데이터 매핑
        assetData.setConsume((String) data.get("consume"));
        assetData.setCash((Integer) data.get("cash"));
        assetData.setStock((Integer) data.get("stock"));
        assetData.setProperty((Integer) data.get("property"));
        assetData.setDeposit((Integer) data.get("deposit"));

        // 대출 정보 설정
        assetData.setLoanAmount((Integer) data.get("loanAmount"));
        assetData.setLoanPurpose((String) data.get("loanPurpose"));
        assetData.setPeriod((Integer) data.get("period"));

        // DB 저장
        if (mypageService.findAssetData(userID)) {
            session.setAttribute("message", "이미 저장된 데이터가 있습니다.");
            return ResponseEntity.status(409).body("이미 저장된 데이터가 있습니다.");
        }
        mypageService.insertAssetData(assetData);

//        // DB에 저장된 데이터 콘솔에 출력 (확인용)
//        System.out.println("DB에 저장된 데이터:");
//        System.out.println("userID: " + assetData.getUserID());
//        System.out.println("Cash: " + assetData.getCash());
//        System.out.println("Stock: " + assetData.getStock());
//        System.out.println("Property: " + assetData.getProperty());
//        System.out.println("Deposit: " + assetData.getDeposit());
//        System.out.println("소비유형: " + assetData.getConsume());
//        System.out.println("대출액: " + assetData.getLoanAmount());
//        System.out.println("대출 목적: " + assetData.getLoanPurpose());
//        System.out.println("대출 기간: " + assetData.getPeriod());

        session.setAttribute("message", "DB 저장 완료");

        return ResponseEntity.ok("저장 완료");
    }

    //update
    @PutMapping("/updateAsset")
    public ResponseEntity<String> updateUserData(HttpSession session, @RequestBody Map<String, Object> data) {
        String userID = (String) session.getAttribute("kakaoId");
        System.out.println("update 수행중:" + userID);

        // 넘어온 데이터 출력
        System.out.println("수정될 데이터: " + data); // 추가된 부분
        AssetVO assetData = new AssetVO();

        if (userID != null) {
            assetData.setUserID(userID);

            // 기존 데이터 조회
            AssetVO existingData = mypageService.getAssetData(userID); // 기존 데이터를 가져오는 서비스 메서드

            // 데이터 매핑
            assetData.setConsume(data.get("consume") != null ? (String) data.get("consume") : existingData.getConsume());
            assetData.setCash(data.get("cash") != null ? (Integer) data.get("cash") : existingData.getCash());
            assetData.setStock(data.get("stock") != null ? (Integer) data.get("stock") : existingData.getStock());
            assetData.setProperty(data.get("property") != null ? (Integer) data.get("property") : existingData.getProperty());
            assetData.setDeposit(data.get("deposit") != null ? (Integer) data.get("deposit") : existingData.getDeposit());
            // 대출 정보 설정
            assetData.setLoanAmount(data.get("loanAmount") != null ? (Integer) data.get("loanAmount") : existingData.getLoanAmount());
            assetData.setLoanPurpose(data.get("loanPurpose") != null ? (String) data.get("loanPurpose") : existingData.getLoanPurpose());
            assetData.setPeriod(data.get("period") != null ? (Integer) data.get("period") : existingData.getPeriod());

            System.out.println("assetData"+assetData);
            mypageService.updateAssetData(assetData);
            session.setAttribute("message", "정보가 성공적으로 수정되었습니다.");
            return ResponseEntity.ok("정보가 성공적으로 수정되었습니다.");
        } else {
            session.setAttribute("message", "사용자 정보를 찾을 수 없습니다.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("사용자 정보를 찾을 수 없습니다.");
        }
    }



    // member 수정
    @PostMapping("/updateMember")
    public String updateUser(MemberVO memberVO, Model model, HttpSession session) {

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

    // == point ==

    // point 조회
    @GetMapping(value = "/points", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Integer> getPoints(HttpSession session) {
        String kakaoId = (String) session.getAttribute("kakaoId");
        System.out.println("points 조회 들어옴");

        if (kakaoId != null) {
            Integer point = mypageService.getPoint(kakaoId);
            if (point != null) {
                System.out.println(point);
                return ResponseEntity.ok(point); // 포인트만 반환
            } else {
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
    // point 출금
    @PostMapping("/withdraw")
    public ResponseEntity<?> withdrawPoints(@RequestBody MemberVO data, HttpSession session) {

        String kakaoId = (String) session.getAttribute("kakaoId");
        if (kakaoId != null) {
            Integer point = mypageService.getPoint(kakaoId);
            // 출금 처리 로직: data.getPoint() : 서버에서 요청한 출금 값
            if (point != null && point >= data.getPoint()) {
                boolean withdrawSuccess = mypageService.withdrawPoints(kakaoId, data.getPoint());
                if (withdrawSuccess) {
                    System.out.println("withdraw success");

                    // cash를 업데이트하는 메서드 호출 (서비스에서 처리됨)
                    boolean addCashSuccess = mypageService.updateCash(kakaoId, data.getPoint());

                    if (addCashSuccess) {
                        return ResponseEntity.ok("출금 및 현금 추가가 성공적으로 완료되었습니다.");
                    } else {
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("현금 추가 실패");
                    }
                } else {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("출금 실패");
                }
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("출금할 포인트가 부족하거나 유효하지 않음");
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("인증되지 않은 사용자");
        }
    }

}

