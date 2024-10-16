package org.solo.mypage.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
import java.util.Map;

@Controller
@RequestMapping("/api/mypage")
@Api(value = "Mypage Controller", tags = "마이페이지 API")
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
    @ApiOperation(value = "자산 불러오기", notes = "사용자의 자산을 불러옵니다")
    public ResponseEntity<?> getAsset(HttpSession session) {
        String userId = getUserId(session);

        if (userId != null) {
            try {
                AssetVO asset = mypageService.getAssetData(userId);
                System.out.println(asset);
                return ResponseEntity.ok(asset);
            } catch (Exception e) {
                return handleError(e);
            }
        } else {
            return handleUnauthorized();
        }
    }

    // 소비유형 수정
    @PutMapping("/updateType")
    @ApiOperation(value = "유형 수정하기", notes = "사용자의 유형을 수정합니다")
    public ResponseEntity<String> updateType(HttpSession session, @RequestBody Map<String, String> data) {
        String userId = getUserId(session);

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
    @ApiOperation(value = "유형 불러오기", notes = "사용자의 유형을 불러옵니다")
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


    @GetMapping("/getBank")
    @ApiOperation(value = "계좌 불러오기", notes = "포인트 출금 시 필요한 계좌정보를 불러옵니다")
    public ResponseEntity<?> getBank(HttpSession session) {
        String userId = getUserId(session);
        if (userId != null) {
            try {
                AssetVO asset = mypageService.getBank(userId);
                return ResponseEntity.ok(asset);
            } catch (Exception e) {
                return handleError(e);
            }
        } else {
            return handleUnauthorized();
        }
    }

    // 포인트 조회
    @GetMapping(value = "/getPoint", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "포인트 불러오기", notes = "사용자의 포인트를 불러옵니다")
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
    @ApiOperation(value = "포인트 차감하기", notes = "사용자의 포인트를 차감합니다")
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
