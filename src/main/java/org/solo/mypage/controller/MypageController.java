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


    // 자산을 불러오는 API
    @GetMapping("/getAsset")
    public ResponseEntity<AssetVO> getAsset(HttpSession session) {
        String userId = (String) session.getAttribute("userId");
        System.out.println("getAsset 수행중: " + userId);

        try {
            AssetVO asset = mypageService.getAssetData(userId); // 리스트 대신 단일 AssetVO 가져오기
            if (asset != null) {
                System.out.println("서비스 불러오기 성공");
                return ResponseEntity.ok(asset);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // 자산이 없을 경우
            }
        } catch (Exception e) {
            System.err.println("오류 발생: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/updateData")
    public ResponseEntity<String> updateData(HttpSession session, @RequestBody AssetVO data) {
        String userId = (String) session.getAttribute("userId");
        System.out.println("getAsset 수행중: " + userId);

        data.setUserId(userId);

        try {
            mypageService.updateAsset(data);
            return ResponseEntity.ok("자산 및 대출 정보가 업데이트되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("업데이트 실패: " + e.getMessage());
        }
    }

    @PostMapping("/updateType")
    public ResponseEntity<String> updateType(HttpSession session, @RequestBody Map<String, String> data) {
        String userId = (String) session.getAttribute("userId");
        System.out.println("updateType controller" + userId + "들어온값" + data);

        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("사용자 인증이 필요합니다.");
        }

        String type = data.get("selectedType"); // 프론트에서 넘어온 Type 값

        try {
            mypageService.updateUserType(userId, type); // 서비스 호출
            return ResponseEntity.ok("유형이 성공적으로 업데이트되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("업데이트 실패: " + e.getMessage());
        }
    }

    @GetMapping("/getType")
    public ResponseEntity<?> getType(HttpSession session) {
        String userId = (String) session.getAttribute("userId");

        // 사용자 인증 확인
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("사용자 인증이 필요합니다.");
        }
        try {
            // 사용자 자산 타입 가져오기
            String type = mypageService.getType(userId);
            return ResponseEntity.ok(type); // 성공적으로 타입을 가져온 경우
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류: " + e.getMessage());
        }
    }

    @GetMapping("/getBank")
    public ResponseEntity<?> getBank(HttpSession session) {
        String userId = (String) session.getAttribute("userId");

        // 사용자 인증 확인
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("사용자 인증이 필요합니다.");
        }
        try {
            // 사용자 자산 타입 가져오기
            System.out.println("getBank");
            List<String> bankList = mypageService.getBank(userId);
            return ResponseEntity.ok(bankList); // 성공적으로 타입을 가져온 경우
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류: " + e.getMessage());
        }
    }


    // == point ==

    // point 조회
    @GetMapping(value = "/points", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Integer> getPoints(HttpSession session) {
        String userId = (String) session.getAttribute("userId");
        System.out.println("points 조회 들어옴");

        if (userId != null) {
            Integer point = mypageService.getPoint(userId);
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


    //    @PostMapping("/insertAsset")
//    public ResponseEntity<String> saveUserData(HttpSession session, @RequestBody Map<String, Object> data) {
//        String userId = (String) session.getAttribute("userId");
//        System.out.println("insert 수행중: " + userId);
//
//        AssetVO assetData = new AssetVO();
//
//        if (userId != null) {
//            assetData.setUserId(userId);
//        }
//        // 데이터 매핑
//        assetData.setConsume((String) data.get("consume"));
//        assetData.setCash((Integer) data.get("cash"));
//        assetData.setStock((Integer) data.get("stock"));
//        assetData.setProperty((Integer) data.get("property"));
//        assetData.setDeposit((Integer) data.get("deposit"));
//
//        // 대출 정보 설정
//        assetData.setLoanAmount((Integer) data.get("loanAmount"));
//        assetData.setLoanPurpose((String) data.get("loanPurpose"));
//        assetData.setPeriod((Integer) data.get("period"));
//
//        // DB 저장
//        if (mypageService.findAssetData(userId)) {
//            session.setAttribute("message", "이미 저장된 데이터가 있습니다.");
//            return ResponseEntity.status(409).body("이미 저장된 데이터가 있습니다.");
//        }
//        mypageService.insertAssetData(assetData);
//
////        // DB에 저장된 데이터 콘솔에 출력 (확인용)
////        System.out.println("DB에 저장된 데이터:");
////        System.out.println("userID: " + assetData.getUserID());
////        System.out.println("Cash: " + assetData.getCash());
////        System.out.println("Stock: " + assetData.getStock());
////        System.out.println("Property: " + assetData.getProperty());
////        System.out.println("Deposit: " + assetData.getDeposit());
////        System.out.println("소비유형: " + assetData.getConsume());
////        System.out.println("대출액: " + assetData.getLoanAmount());
////        System.out.println("대출 목적: " + assetData.getLoanPurpose());
////        System.out.println("대출 기간: " + assetData.getPeriod());
//
//        session.setAttribute("message", "DB 저장 완료");
//
//        return ResponseEntity.ok("저장 완료");
//    }
//
//    //update
//    @PutMapping("/updateAsset")
//    public ResponseEntity<String> updateUserData(HttpSession session, @RequestBody Map<String, Object> data) {
//        String userId = (String) session.getAttribute("userId");
//        System.out.println("update 수행중:" + userId);
//
//        // 넘어온 데이터 출력
//        System.out.println("수정될 데이터: " + data); // 추가된 부분
//        AssetVO assetData = new AssetVO();
//
//        if (userId != null) {
//            assetData.setUserId(userId);
//
//            // 기존 데이터 조회
//            AssetVO existingData = mypageService.getAssetData(userId); // 기존 데이터를 가져오는 서비스 메서드
//
//            // 데이터 매핑
//            assetData.setConsume(data.get("consume") != null ? (String) data.get("consume") : existingData.getConsume());
//            assetData.setCash(data.get("cash") != null ? (Integer) data.get("cash") : existingData.getCash());
//            assetData.setStock(data.get("stock") != null ? (Integer) data.get("stock") : existingData.getStock());
//            assetData.setProperty(data.get("property") != null ? (Integer) data.get("property") : existingData.getProperty());
//            assetData.setDeposit(data.get("deposit") != null ? (Integer) data.get("deposit") : existingData.getDeposit());
//            // 대출 정보 설정
//            assetData.setLoanAmount(data.get("loanAmount") != null ? (Integer) data.get("loanAmount") : existingData.getLoanAmount());
//            assetData.setLoanPurpose(data.get("loanPurpose") != null ? (String) data.get("loanPurpose") : existingData.getLoanPurpose());
//            assetData.setPeriod(data.get("period") != null ? (Integer) data.get("period") : existingData.getPeriod());
//
//            System.out.println("assetData"+assetData);
//            mypageService.updateAssetData(assetData);
//            session.setAttribute("message", "정보가 성공적으로 수정되었습니다.");
//            return ResponseEntity.ok("정보가 성공적으로 수정되었습니다.");
//        } else {
//            session.setAttribute("message", "사용자 정보를 찾을 수 없습니다.");
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("사용자 정보를 찾을 수 없습니다.");
//        }
//    }
//
//

    //    @GetMapping("/{accountIndex}")
//    public ResponseEntity<?> getById(@PathVariable int accountIndex, HttpSession session) {
//
//    }
    //point 출금 처음 포인트양, index -> service.getPoint()
//    @PostMapping("/withdraw")
//    public ResponseEntity<?> withdrawPoints(@RequestBody MemberVO data,
//                                            @RequestParam Integer accountIndex,
//                                            HttpSession session) {
//        // 세션에서 userId 가져오기
//        String userId = (String) session.getAttribute("userId");
//        if (userId != null) {
//            // 서비스 메서드 호출
//            return mypageService.withdrawPoints(userId, accountIndex, data.getPoint());
//        } else {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("인증되지 않은 사용자");
//        }
//    }

}

