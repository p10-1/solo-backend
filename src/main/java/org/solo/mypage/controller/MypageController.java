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

    // 포인트 출금 메서드
    @PostMapping("/withdraw")
    public ResponseEntity<?> withdrawPoints(@RequestBody MemberVO data,
                                            @RequestParam Integer idx,
                                            HttpSession session) {
        String userId = (String) session.getAttribute("userId");
        if (userId != null) {
            return mypageService.withdrawPoints(userId, idx, data.getPoint());
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("인증되지 않은 사용자");
        }
    }

}

