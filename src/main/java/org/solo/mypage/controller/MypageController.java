package org.solo.mypage.controller;

import org.solo.asset.domain.AssetVO;
import org.solo.member.domain.MemberVO;
import org.solo.mypage.service.MypageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
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

    // 에러메세지 통일
    private ResponseEntity<String> handleUnauthorized() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("fail: 인증되지 않은 사용자");
    }

    private ResponseEntity<String> handleError(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("fail: " + e.getMessage());
    }

    private String getUserId(HttpSession session) {
        return (String) session.getAttribute("userId");
    }

    // 자산 불러오기
    @GetMapping("/getAsset")
    public ResponseEntity<?> getAsset(HttpSession session) {
        String userId = getUserId(session);

        if (userId != null) {
            try {
                AssetVO asset = mypageService.getAssetData(userId);
                return ResponseEntity.ok(asset);
            } catch (Exception e) {
                return handleError(e);
            }
        } else {
            return handleUnauthorized();
        }
    }


    // 자산 수정
    @PostMapping("/updateAsset")
    public ResponseEntity<String> updateAsset(HttpSession session, @RequestBody AssetVO data) {
        String userId = getUserId(session);

        if (userId != null) {
            data.setUserId(userId);
            try {
                mypageService.updateAsset(data);
                return ResponseEntity.ok("success");
            } catch (Exception e) {
                return handleError(e);
            }
        } else {
            return handleUnauthorized();
        }
    }

    // 소비유형 수정
    @PostMapping("/updateType")
    public ResponseEntity<String> updateType(HttpSession session, @RequestBody Map<String, String> data) {
        String userId = getUserId(session);
        System.out.println("updateType controller");

        if (userId != null) {
            String type = data.get("selectedType");
            try {
                mypageService.updateUserType(userId, type);
                return ResponseEntity.ok("success");
            } catch (Exception e) {
                return handleError(e);
            }
        } else {
            return handleUnauthorized();
        }
    }

    // 소비유형 불러오기
    @GetMapping("/getType")
    public ResponseEntity<?> getType(HttpSession session) {
        String userId = getUserId(session);

        if (userId != null) {
            try {
                String type = mypageService.getType(userId);
                return ResponseEntity.ok(type);
            } catch (Exception e) {
                return handleError(e);
            }
        } else {
            return handleUnauthorized();
        }
    }

    //
    @GetMapping("/getBank")
    public ResponseEntity<?> getBank(HttpSession session) {
        String userId = getUserId(session);

        if (userId != null) {
            try {
                List<String> bankList = mypageService.getBank(userId);
                return ResponseEntity.ok(bankList);
            } catch (Exception e) {
                return handleError(e);
            }
        } else {
            return handleUnauthorized();
        }
    }

    // 포인트 조회
    @GetMapping(value = "/points", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getPoints(HttpSession session) {
        String userId = getUserId(session);

        if (userId != null) {
            Integer point = mypageService.getPoint(userId);
            return ResponseEntity.ok(point);
        } else {
            return handleUnauthorized();
        }
    }


    // 포인트 출금 메서드
    @PostMapping("/withdraw")
    public ResponseEntity<String> withdrawPoints(@RequestBody MemberVO data,
                                                 @RequestParam Integer idx,
                                                 HttpSession session) {
        String userId = getUserId(session);
        if (userId != null) {
            try {
                mypageService.withdrawPoints(userId, idx, data.getPoint());
                return ResponseEntity.ok("success");
            } catch (Exception e) {
                return handleError(e);
            }
        } else {
            return handleUnauthorized();
        }
    }
}
