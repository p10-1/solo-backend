package org.solo.mypage.controller;

import org.solo.asset.domain.AssetVO;
import org.solo.member.domain.MemberVO;
import org.solo.member.service.MemberService;
import org.solo.mypage.service.MypageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    // update
    @PutMapping("/updateAsset")
    public ResponseEntity<String> updateUserData(HttpSession session, @RequestBody Map<String, Object> data) {
        String userID = (String) session.getAttribute("kakaoId");
        System.out.println("update 수행중:" + userID);

        // AssetVO 객체 생성
        AssetVO assetData = new AssetVO();

        if (userID != null) {
            assetData.setUserID(userID);
            assetData.setConsume((String) data.get("consume"));

            // 자산 금액 추출
            Map<String, Object> assets = (Map<String, Object>) data.get("assets");
            if (assets != null) {
                // 현금 자산
                List<Map<String, Object>> cashAssets = (List<Map<String, Object>>) assets.get("현금자산");
                assetData.setCash(cashAssets != null && !cashAssets.isEmpty() ?
                        ((Number) cashAssets.get(0).get("amount")).intValue() : 0);

                // 증권 자산
                List<Map<String, Object>> stockAssets = (List<Map<String, Object>>) assets.get("증권자산");
                assetData.setStock(stockAssets != null && !stockAssets.isEmpty() ?
                        ((Number) stockAssets.get(0).get("amount")).intValue() : 0);

                // 부동산 자산
                List<Map<String, Object>> propertyAssets = (List<Map<String, Object>>) assets.get("부동산자산");
                assetData.setProperty(propertyAssets != null && !propertyAssets.isEmpty() ?
                        ((Number) propertyAssets.get(0).get("amount")).intValue() : 0);

                // 예적금 자산
                List<Map<String, Object>> depositAssets = (List<Map<String, Object>>) assets.get("예적금자산");
                assetData.setDeposit(depositAssets != null && !depositAssets.isEmpty() ?
                        ((Number) depositAssets.get(0).get("amount")).intValue() : 0);
            } else {
                // assets가 null인 경우 기본값 설정
                assetData.setCash(0);
                assetData.setStock(0);
                assetData.setProperty(0);
                assetData.setDeposit(0);
            }

            // 대출 정보 추출
            Map<String, Object> loan = (Map<String, Object>) data.get("loan");
            if (loan != null) {
                int loanAmount = loan.get("amount") != null ? ((Number) loan.get("amount")).intValue() : 0;
                String loanPurpose = (String) loan.get("purpose");
                int period = loan.get("period") != null ? ((Number) loan.get("period")).intValue() : 0;

                // 대출 정보는 AssetVO에 추가하여 설정
                assetData.setLoanAmount(loanAmount);
                assetData.setLoanPurpose(loanPurpose != null ? loanPurpose : "");
                assetData.setPeriod(period);
            } else {
                // 대출 정보가 null인 경우 기본값 설정
                assetData.setLoanAmount(0);
                assetData.setLoanPurpose("");
                assetData.setPeriod(0);
            }

            // DB 업데이트
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

    // == point ==

    // 포인트 조회
    @GetMapping("/points")
    public ResponseEntity<Integer> getPoint(HttpSession session) {
        String kakaoId = (String) session.getAttribute("kakaoId");

        if (kakaoId != null) {
            MemberVO memberData = mypageService.getPoint(kakaoId);
            if (memberData != null) {
                return ResponseEntity.ok(memberData.getPoint()); // 포인트만 반환
            } else {
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }


//    // 포인트 출금
//    @PostMapping("/withdraw")
//    public ResponseEntity<?> withdrawPoints(@RequestBody WithdrawRequest request) {
//        // 출금 로직
//        boolean success = /* 출금 처리 */;
//        if (success) {
//            return ResponseEntity.ok().build();
//        } else {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
//        }
//    }

}

